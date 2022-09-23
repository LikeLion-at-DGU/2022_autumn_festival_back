package likelion.festival.service;

import likelion.festival.dto.BoothDto;
import likelion.festival.dto.BoothFilterDto;
import likelion.festival.entitiy.Booth;
import likelion.festival.entitiy.BoothLocation;
import likelion.festival.exception.WrongBoothId;
import likelion.festival.repository.BoothRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class BoothService {

    private final BoothRepository boothRepository;

    public List<BoothFilterDto> boothFilter(BoothLocation boothLocation) {
        // TODO : default 위치 설정
        List<Booth> booths = boothRepository.findByBoothLocation(boothLocation);
        List<BoothFilterDto> boothFilterDtos = booths.stream().map(e -> entityToFilterDto(e))
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

    // TODO : 메뉴검색 추가하기
    public List<BoothFilterDto> search(String search) {
        List<Booth> booths = boothRepository.findByTitle(search);
        List<BoothFilterDto> boothFilterDtos = booths.stream().map(e -> entityToFilterDto(e))
                .collect(Collectors.toList());
        return boothFilterDtos;
    }

    //생성
    @Transactional
    public Integer create(BoothDto boothDto) {
        Booth booth = boothDtoToEntity(boothDto);
        boothRepository.save(booth);
        return HttpStatus.CREATED.value();
    }

    //읽기
    public BoothDto read(Long id) {
        Optional<Booth> booth = boothRepository.findById(id);
        if (!booth.isPresent()) {
            throw new WrongBoothId();
        }
        BoothDto boothDto = entityToBoothDto(booth.get());
        return boothDto;
    }

    //수정
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

    BoothFilterDto entityToFilterDto(Booth booth) {
        return BoothFilterDto.builder()
                .id(booth.getId())
                .boothType(booth.getBoothType())
                .title(booth.getTitle())
                .introduction(booth.getIntroduction())
                .boothLocation(booth.getBoothLocation())
                // TODO : like 갯수와 이미지 추가하기
                .build();
    }

    Booth boothDtoToEntity(BoothDto boothDto) {
        return Booth.builder()
                .title(boothDto.getTitle())
                .introduction(boothDto.getIntroduction())
                .boothType(boothDto.getBoothType())
                .boothLocation(boothDto.getBoothLocation())
                .notice(boothDto.getNotice())
                .content(boothDto.getContent())
                .imageId(boothDto.getImageId())
                .startAt(boothDto.getStartAt())
                .endAt(boothDto.getEndAt())
                //TODO : 위치 이미지와 소개 이미지 추가
                .build();
    }

    BoothDto entityToBoothDto(Booth booth) {
        return BoothDto.builder()
                .id(booth.getId())
                .title(booth.getTitle())
                .introduction(booth.getIntroduction())
                .boothType(booth.getBoothType())
                .boothLocation(booth.getBoothLocation())
                .notice(booth.getNotice())
                .content(booth.getContent())
                .imageId(booth.getImageId())
                .startAt(booth.getStartAt())
                .endAt(booth.getEndAt())
                //TODO : 위치 이미지와 소개 이미지 추가
                .build();
    }


}
