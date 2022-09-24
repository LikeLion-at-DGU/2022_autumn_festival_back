package likelion.festival.service;

import likelion.festival.dto.BoothDayLocationDto;
import likelion.festival.dto.BoothDto;
import likelion.festival.dto.BoothFilterDto;
import likelion.festival.entity.Booth;
import likelion.festival.entity.Image;
import likelion.festival.exception.WrongBoothId;
import likelion.festival.repository.BoothRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
        List<BoothFilterDto> boothFilterDtos = booths.stream().map(e -> {
                    LocalDate start = StringToDate(e.getStartAt());
                    LocalDate end = StringToDate(e.getEndAt());
                    LocalDate today = LocalDate.now();
                    BoothFilterDto boothFilterDto = entityToFilterDto(e);
                    if (start.isBefore(today) && end.isAfter(today) || start.isEqual(today) || end.isEqual(today)) {
                        boothFilterDto.setActive(true);
                    } else {
                        boothFilterDto.setActive(false);
                    }
                    boothFilterDto.setIsLike(checkIsLike(request,e.getId()));
                    boothFilterDto.setLikeCnt(e.getLikes().stream().count());
                    return boothFilterDto;
                })
                .collect(Collectors.toList());
        return boothFilterDtos;
    }

    public List<BoothFilterDto> boothTopFive(HttpServletRequest request) {
        List<Booth> booths = boothRepository.findAll();
        List<BoothFilterDto> boothFilterDtos = booths.stream()
                .map(e -> {BoothFilterDto boothFilterDto = entityToFilterDto(e);
                    boothFilterDto.setLikeCnt(e.getLikes().stream().count());
                    boothFilterDto.setIsLike(checkIsLike(request,e.getId()));
                    return boothFilterDto;})
                .sorted(Comparator.comparing(BoothFilterDto::getLikeCnt).reversed())
                .limit(5)
                .collect(Collectors.toList());
        return boothFilterDtos;
    }

    //날짜와 장소로 필터링 하는 기능 ok
    public List<BoothDayLocationDto> boothDayLocation(HttpServletRequest request, String day, String location) {
        HashMap<String, String> date = festivalDate();
        LocalDate today = StringToDate(date.get(day));
        List<Booth> booths = boothRepository.findByLocation(location);
        List<BoothDayLocationDto> result = booths.stream()
                .filter(e -> StringToDate(e.getStartAt()).isBefore(today)
                        && StringToDate(e.getEndAt()).isAfter(today)
                        || StringToDate(e.getStartAt()).isEqual(today)
                        || StringToDate(e.getEndAt()).isEqual(today))
                .map(e -> {
                    BoothDayLocationDto boothDayLocationDto = entityToDayLocationDto(e);
                    boothDayLocationDto.setIsLike(checkIsLike(request, e.getId()));
                    boothDayLocationDto.setLikeCnt(e.getLikes().stream().count());
                    return boothDayLocationDto;
                })
                .collect(Collectors.toList());
        return result;
    }

    //생성 ok
    @Transactional
    public Booth create(BoothDto boothDto) {
        Booth booth = boothDtoToEntity(boothDto);
        Booth newBooth = boothRepository.save(booth);
        return newBooth;
    }

    //읽기 ok
    public BoothDto read(HttpServletRequest request, Long id) {
        Optional<Booth> booth = boothRepository.findById(id);
        if (!booth.isPresent()) {
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
        boothDto.setLikeCnt(booth.get().getLikes().stream().count());
        if (booth.get().getImages().isEmpty()){
            boothDto.setImages(new ArrayList<>());
            return boothDto;
        }
        boothDto.setImages(booth.get().getImages());
        return boothDto;
    }

    //수정 ok
    @Transactional
    public Booth update(Long id, BoothDto boothDto) {
        Optional<Booth> booth = boothRepository.findById(id);
        if (!booth.isPresent()) {
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
        if (!booth.isPresent()) {
            throw new WrongBoothId();
        }
        boothRepository.delete(booth.get());
        return "Ok";
    }

    //TODO : like, menu, comment 관련 비즈니스 로직 작성하기

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
                // TODO : 이미지 추가하기
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
                //TODO : 위치 이미지와 소개 이미지 추가
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
                //TODO : 위치 이미지와 소개 이미지 추가
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
                // TODO : 이미지 추가하기
                .build();
    }

    private Boolean checkIsLike(HttpServletRequest request, Long id){
        Optional<Cookie> boothCookie = likesService.findBoothCookie(request, id);
        if (boothCookie.isPresent()) {
            return true;
        } else{
            return false;
        }
    }

}
