package likelion.festival.controller;

import likelion.festival.dto.*;
import likelion.festival.entity.Booth;
import likelion.festival.entity.Likes;
import likelion.festival.service.BoothService;
import likelion.festival.service.CommentService;
import likelion.festival.service.ImageService;
import likelion.festival.service.LikesService;
import likelion.festival.service.MenuService;
import likelion.festival.util.MD5Generator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RequestMapping("api/booths")
@RestController
public class BoothController {

    private final BoothService boothService;
    private final LikesService likesService;
    private final CommentService commentService;
    private final MenuService menuService;
    private final ImageService imageService;

    @GetMapping(params = {"filter"})
    public List<BoothFilterDto> boothFilter(HttpServletRequest request, @RequestParam String filter) {
        return boothService.boothFilterAndSearch(request, filter);
    }

    @GetMapping("/top3")
    public List<BoothFilterDto> boothTopThree(HttpServletRequest request) {
        return boothService.boothTopThree(request);
    }

    @GetMapping
    public List<BoothDayLocationDto> boothDayLcotion(HttpServletRequest request, @RequestParam String day,
                                                     @RequestParam String location){
        return boothService.boothDayLocation(request, day, location);
    }

    @PostMapping()
    public Integer boothCreate(@RequestPart(value = "imgList",required = false) List<MultipartFile> imgList, @RequestParam(value = "boothDto") BoothDto boothDto) {
        Booth booth = boothService.create(boothDto);
        System.out.println("asc");
        if (imgList==null){
            System.out.println("gd");
            return HttpStatus.OK.value();
        }
        imageService.saveBoothImage(imgList, booth);
        return HttpStatus.OK.value();
    }

    @GetMapping("{id}")
    public BoothDto boothRead(HttpServletRequest request, @PathVariable Long id) {
        return boothService.read(request, id);
    }

    @PutMapping("{id}")
    public Booth boothUpdate(@PathVariable Long id, @RequestBody BoothDto boothDto) {
        return boothService.update(id, boothDto);
    }

    @DeleteMapping("{id}")
    public String boothDelete(@PathVariable Long id) {
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
    public String likeDelete(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response){
        Optional<Cookie> boothCookie = likesService.findBoothCookie(request, id);
        if (boothCookie.isPresent()) {
            Cookie userCookie = boothCookie.get();
            String cookieKey = userCookie.getValue();
            likesService.delete(id, cookieKey);

            Cookie keyCookie = new Cookie(id.toString(), null);
            keyCookie.setMaxAge(0);
            keyCookie.setPath("/");
            response.addCookie(keyCookie);
            }
        else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
        return "Ok";
    }

    @PostMapping("{id}/comments")
    public CommentResponseDto createComment(@PathVariable Long id, @RequestBody CommentRequestDto commentRequestDto){
        return commentService.create(id, commentRequestDto);
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
