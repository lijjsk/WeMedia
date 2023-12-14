package com.lijjsk.comment.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lijjsk.model.common.dtos.ResponseResult;
import com.lijjsk.model.wemedia.comment.dtos.CommentDto;
import com.lijjsk.model.wemedia.comment.pojos.Comment;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    /**
     * 点赞评论
     * @param commentId
     * @return
     */
    public ResponseResult likeComment(Integer commentId);

    /**
     * 取消点赞，或者点踩
     * @param commentId
     * @return
     */
    public ResponseResult dislikeComment(Integer commentId);
}
