package com.lijjsk.social.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lijjsk.model.common.dtos.ResponseResult;
import com.lijjsk.model.wemedia.comment.dtos.CommentReplyDto;
import com.lijjsk.model.wemedia.comment.pojos.CommentReply;

public interface CommentReplyService extends IService<CommentReply> {
    /**
     * 获取评论回复列表
     * @param commentId
     * @return
     */
    public ResponseResult getCommentReplyList(Integer commentId);

    /**
     * 保存评论的回复
     * @param commentReplyDto
     * @return
     */
    public ResponseResult saveCommentReply(CommentReplyDto commentReplyDto);

    /**
     * 删除评论的回复
     * @param replyId
     * @return
     */
    public ResponseResult deleteCommentReply(Integer replyId);

    /**
     * 点赞评论的回复
     * @param replyId
     * @return
     */
    public ResponseResult likeCommentReply(Integer replyId);

    /**
     * 取消点赞或者点踩
     * @param replyId
     * @return
     */
    public ResponseResult dislikeCommentReply(Integer replyId);
}
