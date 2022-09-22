package likelion.festival.service;

import likelion.festival.entitiy.Booth;
import likelion.festival.entitiy.Likes;
import likelion.festival.exception.WrongBoothId;
import likelion.festival.exception.WrongLikesKey;
import likelion.festival.repository.BoothRepository;
import likelion.festival.repository.LikesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@RequiredArgsConstructor
@Service
public class LikesService {

    private final LikesRepository likesRepository;
    private final BoothRepository boothRepository;

    public Likes create(Long id){
        Optional<Booth> booth = boothRepository.findById(id);
        if (!booth.isEmpty()){
            throw new WrongBoothId();
        }
        String newCookieKey = createCookieKey();
        Likes likes = Likes.builder().booth(booth.get()).cookieKey(newCookieKey).build();
        likesRepository.save(likes);

        return likes;
    }

    public void delete(Long boothId, String cookieKey) {
        Optional<Likes> likes = likesRepository.findByCookieKey(cookieKey);
        Optional<Booth> booth = boothRepository.findById(boothId);
        if (!booth.isPresent()) {
            throw new WrongBoothId();
        }
        if (!likes.isPresent()){
            throw new WrongLikesKey();
        }
        boothRepository.deleteById(likes.get().getId());
    }

    private String createCookieKey(){
        while (true) {
            String cookieKey = createRandomString();
            Optional<Likes> likes = likesRepository.findByCookieKey(cookieKey);
            if (likes.isPresent()){
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
}
