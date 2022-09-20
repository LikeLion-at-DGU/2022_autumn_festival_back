package likelion.festival.controller;

import likelion.festival.dto.BoothDto;
import likelion.festival.dto.BoothFilterDto;
import likelion.festival.entitiy.Booth;
import likelion.festival.entitiy.BoothLocation;
import likelion.festival.service.BoothService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("api/booths")
@RestController
public class BoothController {

    private final BoothService boothService;

    @GetMapping(params = {"filter"})
    public List<BoothFilterDto> boothFilter(@RequestParam BoothLocation filter) {
        return boothService.boothFilter(filter);
    }

    @GetMapping("/top3")
    public List<BoothFilterDto> boothTopThree() {
        return boothService.boothTopThree();
    }

    @GetMapping(params = {"search"})
    public List<BoothFilterDto> boothSearch(@RequestParam String search) {
        return boothService.search(search);
    }

    @PostMapping()
    public Integer boothCreate(@RequestBody BoothDto boothDto) {
        return boothService.create(boothDto);
    }

    @GetMapping("{id}")
    public BoothDto boothRead(@PathVariable Long id) {
        return boothService.read(id);
    }

    @PutMapping("{id}")
    public Booth boothUpdate(@PathVariable Long id, @RequestBody BoothDto boothDto) {
        return boothService.update(id, boothDto);
    }

    @DeleteMapping("{id}")
    public Integer boothDelete(@PathVariable Long id) {
        return boothService.delete(id);
    }

    // TODO : like, menu, comment controller 추가하기

}
