package likelion.festival.service;

import likelion.festival.dto.BoothDayLocationDto;
import likelion.festival.dto.BoothDto;
import likelion.festival.dto.BoothFilterDto;
import likelion.festival.entitiy.Booth;
import likelion.festival.exception.WrongBoothId;
import likelion.festival.repository.BoothRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class BoothService {

    private final BoothRepository boothRepository;


    // TODO : 메뉴검색 추가하기
    public List<BoothFilterDto> boothFilterAndSearch(String search) {
        List<Booth> booths = boothRepository.findByTitle(search);
        if (booths.isEmpty()) {
            booths = boothRepository.findByLocation(search);
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

                    return boothFilterDto;
                })
                .collect(Collectors.toList());
        return boothFilterDtos;
    }

    // TODO : 좋아요 기준으로 top3 추출하는 방식으로 refactoring (현재는 타이틀 기준으로 3개 추출한 코드)
    public List<BoothFilterDto> boothTopThree() {
        List<Booth> booths = boothRepository.findByTop3();
        List<BoothFilterDto> boothFilterDtos = booths.stream().limit(3).map(e -> entityToFilterDto(e))
                .collect(Collectors.toList());
        return boothFilterDtos;
    }

    //날짜와 장소로 필터링 하는 기능 ok
    public List<BoothDayLocationDto> boothDayLocation(String day, String location) {
        HashMap<String, String> date = festivalDate();
        LocalDate today = StringToDate(date.get(day));
        List<Booth> booths = boothRepository.findByLocation(location);
        List<BoothDayLocationDto> result = booths.stream()
                .filter(e -> StringToDate(e.getStartAt()).isBefore(today)
                        && StringToDate(e.getEndAt()).isAfter(today)
                        || StringToDate(e.getStartAt()).isEqual(today)
                        || StringToDate(e.getEndAt()).isEqual(today))
                .map(e -> entityToDayLocationDto(e))
                .collect(Collectors.toList());
        return result;
    }

    //생성 ok
    @Transactional
    public Long create(BoothDto boothDto) {
        Booth booth = boothDtoToEntity(boothDto);
        boothRepository.save(booth);
        return booth.getId();
    }

    //읽기 ok
    public BoothDto read(Long id) {
        Optional<Booth> booth = boothRepository.findById(id);
        if (!booth.isPresent()) {
            throw new WrongBoothId();
        }
        BoothDto boothDto = entityToBoothDto(booth.get());
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
    public Integer delete(Long id) {
        Optional<Booth> booth = boothRepository.findById(id);
        if (!booth.isPresent()) {
            throw new WrongBoothId();
        }
        boothRepository.delete(booth.get());
        return HttpStatus.OK.value();
    }

    //TODO : like, menu, comment 관련 비즈니스 로직 작성하기

    public HashMap<String, String> festivalDate() {
        HashMap<String, String> date = new HashMap<>();
        date.put("WED", "2022-09-28");
        date.put("THU", "2022-09-29");
        date.put("FRI", "2022-09-30");
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
                // TODO : like 갯수와 이미지 추가하기
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
                // TODO : like 갯수와 이미지 추가하기
                .build();
    }

}
