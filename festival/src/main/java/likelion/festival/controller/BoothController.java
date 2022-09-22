package likelion.festival.controller;

import likelion.festival.dto.*;
import likelion.festival.entitiy.Booth;
import likelion.festival.entitiy.BoothLocation;
import likelion.festival.service.BoothService;
import likelion.festival.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("api/booths")
@RestController
public class BoothController {

    private final BoothService boothService;
    private final CommentService commentService;

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

    @PostMapping("{id}/comments")
    public CommentResponseDto createComment(@PathVariable Long id, @RequestBody CommentRequestDto commentRequestDto){
        return commentService.create(id, commentRequestDto);
    }

    @DeleteMapping("{id}/comments/{comment_id}")
    public String deleteComment(@PathVariable Long id, @PathVariable Long comment_id, @RequestBody CommentPasswordDto password){
        return commentService.delete(comment_id, password);
    }

    @DeleteMapping("{id}/comments/{comment_id}/force")
    public String deleteForceComment(@PathVariable Long id, @PathVariable Long comment_id){
        return commentService.force_delete(comment_id);
    }

    @GetMapping("{id}/comments")
    public List<CommentResponseDto> getCommentList(@PathVariable Long id){
        return commentService.getAll(id);
    }

    // TODO : like, menu, comment controller 추가하기

}
