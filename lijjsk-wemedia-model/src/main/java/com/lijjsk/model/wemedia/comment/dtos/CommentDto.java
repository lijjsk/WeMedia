package com.lijjsk.model.wemedia.comment.dtos;

import lombok.Data;

@Data
public class CommentDto {
    /**
     * 用户id
     */
    private Integer userId;
    /**
     * 视频id
     */
    private Integer videoId;
    /**
     * 评论内容
     */
    private String content;
}
