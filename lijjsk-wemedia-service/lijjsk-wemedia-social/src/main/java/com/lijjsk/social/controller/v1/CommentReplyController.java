package com.lijjsk.social.controller.v1;

import com.lijjsk.model.common.dtos.ResponseResult;
import com.lijjsk.model.wemedia.comment.dtos.CommentReplyDto;
import com.lijjsk.social.service.CommentReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wemedia/commentReply")
public class CommentReplyController {
    @Autowired
    private CommentReplyService commentReplyService;
    @GetMapping("/get/commentReply")
    public ResponseResult getCommentReplyList(@RequestParam("commentId") Integer commentId){
        return commentReplyService.getCommentReplyList(commentId);
    }
    @PostMapping("/save/commentReply")
    public ResponseResult saveCommentReply(@RequestBody CommentReplyDto commentReplyDto){
        return commentReplyService.saveCommentReply(commentReplyDto);
    }
    @DeleteMapping("/delete/commentReply")
    public ResponseResult deleteCommentReply(@RequestParam("commentReplyId") Integer replyId){
        return commentReplyService.deleteCommentReply(replyId);
    }
    @PutMapping("/like/commentReply")
    public ResponseResult likeCommentReply(@RequestParam("commentReplyId")Integer replyId){
        return commentReplyService.likeCommentReply(replyId);
    }
    @PutMapping("/dislike/commentReply")
    public ResponseResult dislikeCommentReply(@RequestParam("commentReplyId")Integer replyId){
        return  commentReplyService.dislikeCommentReply(replyId);
    }

}
