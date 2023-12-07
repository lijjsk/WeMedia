package com.lijjsk.comment.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lijjsk.model.common.dtos.ResponseResult;
import com.lijjsk.model.wemedia.comment.dtos.CommentDto;
import com.lijjsk.model.wemedia.comment.pojos.Comment;

public interface CommentService extends IService<Comment> {
    /**
     * 保存评论
     * @param commentDto
     * @return
     */
    public ResponseResult saveComment(CommentDto commentDto);

    /**
     * 删除评论
     * @param commentId
     * @return
     */
    public ResponseResult deleteComment(Integer commentId);

    /**
     * 获取评论列表
     * @param videoId
     * @return
     */
    public ResponseResult getCommentList(Integer videoId);
}
