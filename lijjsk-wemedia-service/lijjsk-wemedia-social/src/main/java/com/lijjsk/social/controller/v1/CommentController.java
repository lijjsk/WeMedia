package com.lijjsk.social.controller.v1;

import com.lijjsk.model.common.dtos.ResponseResult;
import com.lijjsk.model.wemedia.comment.dtos.CommentDto;
import com.lijjsk.social.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wemedia/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping("/save/comment")
    public ResponseResult saveComment(@RequestBody CommentDto commentDto){
        return commentService.saveComment(commentDto);
    }
    @DeleteMapping("/delete/comment")
    public ResponseResult deleteComment(@RequestParam Integer commentId){
        return commentService.deleteComment(commentId);
    }
    @GetMapping("/get/comment")
    public ResponseResult getComment(@RequestParam Integer videoId){
        return commentService.getCommentList(videoId);
    }
    @PutMapping("/like/comment")
    public ResponseResult likeComment(@RequestParam("commentId") Integer commentId){
        return commentService.likeComment(commentId);
    }
    @PutMapping("/dislike/comment")
    public ResponseResult dislikeComment(@RequestParam("commentId") Integer commentId){
        return commentService.dislikeComment(commentId);
    }
}
