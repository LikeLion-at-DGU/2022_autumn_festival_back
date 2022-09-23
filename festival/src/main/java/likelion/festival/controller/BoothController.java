package likelion.festival.controller;

import likelion.festival.dto.*;
import likelion.festival.entitiy.Booth;
import likelion.festival.entitiy.Likes;
import likelion.festival.service.BoothService;
import likelion.festival.service.CommentService;
import likelion.festival.service.ImageService;
import likelion.festival.service.LikesService;
import likelion.festival.util.MD5Generator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("api/booths")
@RestController
public class BoothController {

    private final BoothService boothService;
    private final LikesService likesService;
    private final CommentService commentService;
    private final ImageService imageService;

    @GetMapping(params = {"filter"})
    public List<BoothFilterDto> boothFilter(@RequestParam String filter) {
        return boothService.boothFilterAndSearch(filter);
    }

    @GetMapping("/top3")
    public List<BoothFilterDto> boothTopThree() {
        return boothService.boothTopThree();
    }

    @GetMapping
    public List<BoothDayLocationDto> boothDayLcotion(@RequestParam String day, @RequestParam String location){
        return boothService.boothDayLocation(day, location);
    }

    @PostMapping()
    public Integer boothCreate(@RequestPart(value = "images",required = false) MultipartFile images, @RequestParam(value = "boothDto") BoothDto boothDto) {
        if (images == null) {
            boothService.create(boothDto);
            return HttpStatus.OK.value();
        }
        try {
            String origFilename = images.getOriginalFilename();
            String servFilename = new MD5Generator(origFilename).toString();

            String savePath = System.getProperty("user.dir") + "/files";


            if (!new File(savePath).exists()) {
                try {
                    new File(savePath).mkdir();
                } catch (Exception e) {
                    e.getStackTrace();
                }
            }
            String imagePath = savePath + "/" + servFilename + ".jpg";
            images.transferTo(new File(imagePath));

            ImageDto imageDto = new ImageDto();
            imageDto.setOriginFileName(origFilename);
            imageDto.setServerFileName(servFilename);
            imageDto.setStoredFilePath(imagePath);

            Long imageId = imageService.saveImage(imageDto);
            boothDto.setImageId(imageId);
            boothService.create(boothDto);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return HttpStatus.OK.value();
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


    // TODO : menu controller 추가하기
}
