package likelion.festival.controller;

import likelion.festival.dto.*;
import likelion.festival.entity.Booth;
import likelion.festival.entity.Likes;
import likelion.festival.service.BoothService;
import likelion.festival.service.CommentService;
import likelion.festival.service.ImageService;
import likelion.festival.service.LikesService;
import likelion.festival.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.CookieGenerator;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RequestMapping("${app-booth}")
@Controller
public class BoothController {

    private final BoothService boothService;
    private final LikesService likesService;
    private final CommentService commentService;
    private final MenuService menuService;
    private final ImageService imageService;

    @ResponseBody
    @GetMapping(params = {"filter"})
    public List<BoothFilterDto> boothFilter(HttpServletRequest request, @RequestParam String filter) {
        return boothService.boothFilterAndSearch(request, filter);
    }

    @ResponseBody
    @GetMapping("/top5")
    public List<BoothFilterDto> boothTopFive(HttpServletRequest request) {
        return boothService.boothTopFive(request);
    }

    @ResponseBody
    @GetMapping
    public List<BoothDayLocationDto> boothDayLocation(HttpServletRequest request, @RequestParam String day,
                                                     @RequestParam String location){
        return boothService.boothDayLocation(request, day, location);
    }

    @ResponseBody
    @PostMapping()
    public Integer boothCreate(@RequestPart(value = "imgList",required = false) List<MultipartFile> imgList, @RequestParam(value = "boothDto") BoothDto boothDto) {
        Booth booth = boothService.create(boothDto);
        if (imgList==null){
            return HttpStatus.OK.value();
        }
        imageService.saveBoothImage(imgList, booth);
        return HttpStatus.OK.value();
    }

    @ResponseBody
    @GetMapping("{id}")
    public BoothDto boothRead(HttpServletRequest request, @PathVariable Long id) {
        return boothService.read(request, id);
    }

    @ResponseBody
    @PutMapping("{id}")
    public Booth boothUpdate(@PathVariable Long id, @RequestBody BoothDto boothDto) {
        return boothService.update(id, boothDto);
    }

    @ResponseBody
    @DeleteMapping("{id}")
    public String boothDelete(@PathVariable Long id) {
        return boothService.delete(id);
    }

    @ResponseBody
    @PostMapping("/{id}/likes")
    public LikesResponseDto likeCreate(@PathVariable Long id, HttpServletResponse response){
        LikesResponseDto likes = likesService.create(id);
        response.setHeader("Set-Cookie",id.toString() + "=" + likes.getCookieKey()
                + ";Max-Age=432000;Path=/;SameSite=None;");
        return likes;
    }

    @ResponseBody
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

    @ResponseBody
    @PostMapping("{id}/comments")
    public CommentResponseDto createComment(@PathVariable Long id, @RequestBody CommentRequestDto commentRequestDto, HttpServletRequest request){
        return commentService.create(id, commentRequestDto, request);
    }

    @ResponseBody
    @GetMapping("{id}/comments")
    public List<CommentResponseDto> getCommentList(@PathVariable Long id){
        return commentService.getAll(id);
    }

    @ResponseBody
    @GetMapping("{id}/menus")
    public List<MenuResponseDto> getMenuList(@PathVariable Long id){
        return menuService.getAll(id);
    }

    @ResponseBody
    @PostMapping("{id}/menus")
    public MenuResponseDto createMenu(@PathVariable Long id,  @RequestBody MenuRequestDto menuRequestDto){
        return menuService.create(id, menuRequestDto);
    }
}
