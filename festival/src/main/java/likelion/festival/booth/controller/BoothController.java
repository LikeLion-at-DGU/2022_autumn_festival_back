package likelion.festival.booth.controller;

import likelion.festival.booth.dto.BoothDayLocationDto;
import likelion.festival.booth.dto.BoothDto;
import likelion.festival.booth.dto.BoothFilterDto;
import likelion.festival.comment.dto.CommentRequestDto;
import likelion.festival.comment.dto.CommentResponseDto;
import likelion.festival.booth.entity.Booth;
import likelion.festival.booth.service.BoothService;
import likelion.festival.likes.dto.LikesResponseDto;
import likelion.festival.menu.dto.MenuRequestDto;
import likelion.festival.menu.dto.MenuResponseDto;
import likelion.festival.comment.service.CommentService;
import likelion.festival.image.service.ImageService;
import likelion.festival.likes.service.LikesService;
import likelion.festival.menu.service.MenuService;
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
@RequestMapping("api/booth")
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

    @GetMapping("/top5")
    public List<BoothFilterDto> boothTopFive(HttpServletRequest request) {
        return boothService.boothTopFive(request);
    }

    @GetMapping
    public List<BoothDayLocationDto> boothDayLocation(HttpServletRequest request, @RequestParam String day,
                                                     @RequestParam String location){
        return boothService.boothDayLocation(request, day, location);
    }

    @PostMapping()
    public Integer boothCreate(@RequestPart(value = "imgList",required = false) List<MultipartFile> imgList, @RequestParam(value = "boothDto") BoothDto boothDto) {
        Booth booth = boothService.create(boothDto);
        if (imgList==null){
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
    public LikesResponseDto likeCreate(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response){
        Optional<Cookie> boothCookie = likesService.findBoothCookie(request, id);
        if(boothCookie.isPresent()){
            throw new IllegalArgumentException("이미 쿠키 있음");
        }
        LikesResponseDto likes = likesService.create(id);
        Cookie keyCokkie = new Cookie(id.toString(), likes.getCookieKey());
        keyCokkie.setMaxAge(7*60*60*24);
        keyCokkie.setPath("/");
        response.addCookie(keyCokkie);
        return likes;
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
    public CommentResponseDto createComment(@PathVariable Long id, @RequestBody CommentRequestDto commentRequestDto, HttpServletRequest request){
        return commentService.create(id, commentRequestDto, request);
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
