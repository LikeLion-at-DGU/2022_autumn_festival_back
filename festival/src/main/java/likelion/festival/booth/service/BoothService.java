package likelion.festival.booth.service;

import likelion.festival.booth.dto.BoothDayLocationDto;
import likelion.festival.booth.dto.BoothDto;
import likelion.festival.booth.dto.BoothFilterDto;
import likelion.festival.booth.entity.Booth;
import likelion.festival.global.exception.WrongBoothId;
import likelion.festival.booth.repository.BoothRepository;
import likelion.festival.likes.service.LikesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class BoothService {

    private final BoothRepository boothRepository;
    private final LikesService likesService;

    public List<BoothFilterDto> boothFilterAndSearch(HttpServletRequest request, String search) {
        List<Booth> booths = boothRepository.findByTitleContaining(search);
        if (booths.isEmpty()) {
            booths = boothRepository.findByLocation(search);
        }
        if(booths.isEmpty()){
            booths = boothRepository.findByMenus_NameContaining(search);
        }
        return booths.stream().map(e -> {
                    BoothFilterDto boothFilterDto = entityToFilterDto(e);
                    boothFilterDto.setIsLike(checkIsLike(request,e.getId()));
                    return boothFilterDto;
                })
                .collect(Collectors.toList());
    }

    public List<BoothFilterDto> boothTopFive(HttpServletRequest request) {
        List<Booth> booths = boothRepository.findAll();
        return booths.stream()
                .map(e -> {BoothFilterDto boothFilterDto = entityToFilterDto(e);
                    boothFilterDto.setIsLike(checkIsLike(request,e.getId()));
                    return boothFilterDto;})
                .filter(boothFilterDto -> boothFilterDto.getActive().equals(true))
                .sorted(Comparator.comparing(BoothFilterDto::getLikeCnt).reversed())
                .limit(5)
                .collect(Collectors.toList());
    }

    //날짜와 장소로 필터링 하는 기능 ok
    public List<BoothDayLocationDto> boothDayLocation(HttpServletRequest request, String day, String location) {
        HashMap<String, String> date = festivalDate();
        LocalDate today = StringToDate(date.get(day));
        List<Booth> booths = boothRepository.findByLocation(location);
        return booths.stream()
                .filter(e -> StringToDate(e.getStartAt()).isBefore(today)
                        && StringToDate(e.getEndAt()).isAfter(today)
                        || StringToDate(e.getStartAt()).isEqual(today)
                        || StringToDate(e.getEndAt()).isEqual(today))
                .map(e -> {
                    BoothDayLocationDto boothDayLocationDto = entityToDayLocationDto(e);
                    boothDayLocationDto.setIsLike(checkIsLike(request, e.getId()));
                    return boothDayLocationDto;
                })
                .collect(Collectors.toList());
    }

    //생성 ok
    @Transactional
    public Booth create(BoothDto boothDto) {
        Booth booth = boothDtoToEntity(boothDto);
        return boothRepository.save(booth);
    }

    //읽기 ok
    public BoothDto read(HttpServletRequest request, Long id) {
        Optional<Booth> booth = boothRepository.findById(id);
        if (booth.isEmpty()) {
            throw new WrongBoothId();
        }
        BoothDto boothDto = entityToBoothDto(booth.get());

        List<Integer> days = new ArrayList<>();
        String startDate = boothDto.getStartAt();
        String endDate = boothDto.getEndAt();
        int start = Integer.parseInt(startDate.substring(startDate.length()-2));
        int end = Integer.parseInt(endDate.substring(startDate.length()-2));
        int minus = end - start;
        days.add(start);
        for(int i = 1; i <= minus; i++){
            days.add(start + i);
        }
        boothDto.setDays(days);
        boothDto.setIsLike(checkIsLike(request, id));
        return boothDto;
    }

    //수정 ok
    @Transactional
    public Booth update(Long id, BoothDto boothDto) {
        Optional<Booth> booth = boothRepository.findById(id);
        if (booth.isEmpty()) {
            throw new WrongBoothId();
        }
        long boothId = booth.get().getId();
        boothDto.setId(boothId);
        Booth updateBooth = boothDtoToEntity(boothDto);
        boothRepository.save(updateBooth);
        return updateBooth;
    }

    //삭제
    @Transactional
    public String delete(Long id) {
        Optional<Booth> booth = boothRepository.findById(id);
        if (booth.isEmpty()) {
            throw new WrongBoothId();
        }
        boothRepository.delete(booth.get());
        return "Ok";
    }

    public HashMap<String, String> festivalDate() {
        HashMap<String, String> date = new HashMap<>();
        date.put("1", "2022-09-28");
        date.put("2", "2022-09-29");
        date.put("3", "2022-09-30");
        return date;
    }

    public LocalDate StringToDate(String date) {
        return LocalDate.parse(date);
    }

    public BoothFilterDto entityToFilterDto(Booth booth) {
        return BoothFilterDto.builder()
                .id(booth.getId())
                .boothType(booth.getBoothType())
                .title(booth.getTitle())
                .location(booth.getLocation())
                .boothNo(booth.getBoothNo())
                .introduction(booth.getIntroduction())
                .active(checkActive(booth))
                .likeCnt((long) booth.getLikes().size())
                .images(booth.getImages())
                .build();
    }

    public Booth boothDtoToEntity(BoothDto boothDto) {
        return Booth.builder()
                .id(boothDto.getId())
                .title(boothDto.getTitle())
                .introduction(boothDto.getIntroduction())
                .boothType(boothDto.getBoothType())
                .location(boothDto.getLocation())
                .boothNo(boothDto.getBoothNo())
                .notice(boothDto.getNotice())
                .content(boothDto.getContent())
                .images(boothDto.getImages())
                .startAt(boothDto.getStartAt())
                .endAt(boothDto.getEndAt())
                .build();
    }

    public BoothDto entityToBoothDto(Booth booth) {
        return BoothDto.builder()
                .id(booth.getId())
                .title(booth.getTitle())
                .introduction(booth.getIntroduction())
                .boothType(booth.getBoothType())
                .location(booth.getLocation())
                .boothNo(booth.getBoothNo())
                .notice(booth.getNotice())
                .content(booth.getContent())
                .startAt(booth.getStartAt())
                .endAt(booth.getEndAt())
                .likeCnt(booth.getLikes().size())
                .images(booth.getImages())
                .build();
    }

    public BoothDayLocationDto entityToDayLocationDto(Booth booth){
        return BoothDayLocationDto.builder()
                .id(booth.getId())
                .boothType(booth.getBoothType())
                .title(booth.getTitle())
                .location(booth.getLocation())
                .boothNo(booth.getBoothNo())
                .introduction(booth.getIntroduction())
                .likeCnt((long) booth.getLikes().size())
                .images(booth.getImages())
                .build();
    }

    private Boolean checkIsLike(HttpServletRequest request, Long id){
        Optional<Cookie> boothCookie = likesService.findBoothCookie(request, id);
        return boothCookie.isPresent();
    }

    private Boolean checkActive(Booth booth){
        LocalDate start = StringToDate(booth.getStartAt());
        LocalDate end = StringToDate(booth.getEndAt());
        LocalDate today = LocalDate.now();
        return start.isBefore(today) && end.isAfter(today) || start.isEqual(today) || end.isEqual(today);
    }

}
