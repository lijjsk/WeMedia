package com.lijjsk.comment.controller.v1;

import com.lijjsk.comment.service.CommentService;
import com.lijjsk.model.common.dtos.ResponseResult;
import com.lijjsk.model.wemedia.comment.dtos.CommentDto;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/wemedia/comment")
@CrossOrigin("*")
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
}
