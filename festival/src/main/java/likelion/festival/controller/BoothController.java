package likelion.festival.controller;

import likelion.festival.dto.*;
import likelion.festival.entitiy.Booth;
import likelion.festival.entitiy.BoothLocation;
import likelion.festival.entitiy.Likes;
import likelion.festival.service.BoothService;
import likelion.festival.service.CommentService;
import likelion.festival.service.LikesService;
import likelion.festival.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("api/booths")
@RestController
public class BoothController {

    private final BoothService boothService;
    private final LikesService likesService;
    private final CommentService commentService;
    private final MenuService menuService;

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

    @PostMapping("/{id}/likes")
    public void likeCreate(@PathVariable Long id, HttpServletResponse response){
        Likes likes = likesService.create(id);
        Cookie keyCookie = new Cookie(id.toString(), likes.getCookieKey());
        keyCookie.setMaxAge(7 * 60 * 60 * 24);
        keyCookie.setPath("/");
        response.addCookie(keyCookie);
    }

    @DeleteMapping("/{id}/likes")
    public void likeDelete(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response){
        Cookie[] userCookies = request.getCookies();
        Boolean complete = false;
        for (Cookie userCookie : userCookies) {
            if (userCookie.getName().equals(id.toString())) {
                String cookieKey = userCookie.getValue();
                likesService.delete(id, cookieKey);
                Cookie keyCookie = new Cookie(id.toString(), null);
                keyCookie.setMaxAge(0);
                keyCookie.setPath("/");
                response.addCookie(keyCookie);
                complete = true;
            }
        }
        if (!complete){
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @PostMapping("{id}/comments")
    public CommentResponseDto createComment(@PathVariable Long id, @RequestBody CommentRequestDto commentRequestDto){
        return commentService.create(id, commentRequestDto);
    }

    @DeleteMapping("{id}/comments/{comment_id}")
    public String deleteComment(@PathVariable Long id, @PathVariable Long comment_id, @RequestBody
            CommentPasswordDto password){
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

    @GetMapping("{id}/menus")
    public List<MenuResponseDto> getMenuList(@PathVariable Long id){
        return menuService.getAll(id);
    }

    @PostMapping("{id}/menus")
    public MenuResponseDto createMenu(@PathVariable Long id,  @RequestBody MenuRequestDto menuRequestDto){
        return menuService.create(id, menuRequestDto);
    }
}
