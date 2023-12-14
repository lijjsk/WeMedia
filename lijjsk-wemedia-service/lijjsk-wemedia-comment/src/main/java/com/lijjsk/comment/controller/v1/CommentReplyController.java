package com.lijjsk.comment.controller.v1;

import com.lijjsk.comment.service.CommentReplyService;
import com.lijjsk.model.common.dtos.ResponseResult;
import com.lijjsk.model.wemedia.comment.dtos.CommentReplyDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/wemedia/comment/reply")
@CrossOrigin("*")
public class CommentReplyController {
    @Autowired
    private CommentReplyService commentReplyService;
    @GetMapping("/get/comment/reply")
    public ResponseResult getCommentReplyList(@RequestParam("commentId") Integer commentId){
        return commentReplyService.getCommentReplyList(commentId);
    }
    @PostMapping("/save/comment/reply")
    public ResponseResult saveCommentReply(@RequestBody CommentReplyDto commentReplyDto){
        return commentReplyService.saveCommentReply(commentReplyDto);
    }
    @DeleteMapping("/delete/comment/reply")
    public ResponseResult deleteCommentReply(@RequestParam("commentReplyId") Integer replyId){
        return commentReplyService.deleteCommentReply(replyId);
    }
    @PutMapping("/like/comment/reply")
    public ResponseResult likeCommentReply(@RequestParam("commentReplyId")Integer replyId){
        return commentReplyService.likeCommentReply(replyId);
    }
    @PutMapping("/dislike/comment/reply")
    public ResponseResult dislikeCommentReply(@RequestParam("commentReplyId")Integer replyId){
        return  commentReplyService.dislikeCommentReply(replyId);
    }

}
