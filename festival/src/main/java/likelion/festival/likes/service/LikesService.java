package likelion.festival.likes.service;

import likelion.festival.likes.dto.LikesResponseDto;
import likelion.festival.booth.entity.Booth;
import likelion.festival.likes.entity.Likes;
import likelion.festival.global.exception.WrongBoothId;
import likelion.festival.global.exception.WrongLikesKey;
import likelion.festival.booth.repository.BoothRepository;
import likelion.festival.likes.repository.LikesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.Random;

@RequiredArgsConstructor
@Service
public class LikesService {

    private final LikesRepository likesRepository;
    private final BoothRepository boothRepository;

    public LikesResponseDto create(Long id){
        Optional<Booth> booth = boothRepository.findById(id);
        if (booth.isEmpty()){
            throw new WrongBoothId();
        }
        String newCookieKey = createCookieKey();
        Likes likes = Likes.builder().booth(booth.get()).cookieKey(newCookieKey).build();
        Likes newLikes = likesRepository.save(likes);

        return entityToDto(newLikes);
    }

    public void delete(Long boothId, String cookieKey) {
        Optional<Booth> booth = boothRepository.findById(boothId);
        if (booth.isEmpty()) {
            throw new WrongBoothId();
        }
        Optional<Likes> likes = likesRepository.findByCookieKey(cookieKey);
        if (likes.isEmpty()){
            throw new WrongLikesKey();
        }
        likesRepository.deleteById(likes.get().getId());
    }

    private String createCookieKey(){
        while (true) {
            String cookieKey = createRandomString();
            Optional<Likes> likes = likesRepository.findByCookieKey(cookieKey);
            if (likes.isEmpty()){
                return cookieKey;
            }
        }
    }

    private String createRandomString(){
        int targetStringLength = 10;
        Random random = new Random();
        return random.ints(97, 123)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    public Optional<Cookie> findBoothCookie(HttpServletRequest request, Long id){
        Cookie[] userCookies = request.getCookies();
        if (userCookies == null){
            return Optional.empty();
        }
        for (Cookie userCookie : userCookies) {
            if (userCookie.getName().equals(id.toString())){
                return Optional.of(userCookie);
            }
        }
        return Optional.empty();
    }

    private LikesResponseDto entityToDto(Likes likes){
        return LikesResponseDto.builder()
                .boothId(likes.getBooth().getId())
                .cookieKey(likes.getCookieKey())
                .build();
    }
}
