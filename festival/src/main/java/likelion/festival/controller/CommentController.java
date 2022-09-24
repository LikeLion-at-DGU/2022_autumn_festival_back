package likelion.festival.controller;

import likelion.festival.dto.*;
import likelion.festival.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("api/comments")
@RestController
public class CommentController {

    private final CommentService commentService;

    @DeleteMapping("/{id}")
    public String deleteComment(@PathVariable Long id, @RequestBody
            CommentPasswordDto password){
        return commentService.delete(id, password);
    }

    @DeleteMapping("{id}/force")
    public String deleteForceComment(@PathVariable Long id){
        return commentService.force_delete(id);
    }
}
