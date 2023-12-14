package com.lijjsk.model.wemedia.comment.dtos;

import lombok.Data;

@Data
public class CommentReplyDto {
    /**
     * 回复的用户id
     */
    private Integer userId;
    /**
     * 回复的评论id
     */
    private Integer commentId;
    /**
     * 回复内容
     */
    private String content;
}
