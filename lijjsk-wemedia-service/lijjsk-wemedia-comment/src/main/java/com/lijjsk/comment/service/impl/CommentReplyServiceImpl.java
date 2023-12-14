package com.lijjsk.comment.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lijjsk.comment.mapper.CommentMapper;
import com.lijjsk.comment.mapper.CommentReplyMapper;
import com.lijjsk.comment.mapper.UserMapper;
import com.lijjsk.comment.service.CommentReplyService;
import com.lijjsk.common.constants.CommonConstants;
import com.lijjsk.model.advertising.pojos.Advertisement;
import com.lijjsk.model.common.dtos.ResponseResult;
import com.lijjsk.model.common.enums.AppHttpCodeEnum;
import com.lijjsk.model.wemedia.comment.dtos.CommentReplyDto;
import com.lijjsk.model.wemedia.comment.pojos.Comment;
import com.lijjsk.model.wemedia.comment.pojos.CommentReply;
import com.lijjsk.model.wemedia.user.pojos.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class CommentReplyServiceImpl extends ServiceImpl<CommentReplyMapper, CommentReply> implements CommentReplyService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private CommentMapper commentMapper;
    /**
     * 获取评论回复列表
     *
     * @param commentId
     * @return
     */
    @Override
    public ResponseResult getCommentReplyList(Integer commentId) {
        if (commentId == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        QueryWrapper<CommentReply> queryWrapper = new QueryWrapper<>();

        // 设置查询条件：parentId 等于传入的 commentId
        queryWrapper.eq("comment_id", commentId);

        List<CommentReply> replyList = list(queryWrapper);

        // 处理查询结果并返回
        return ResponseResult.okResult(replyList);
    }

    /**
     * 保存评论的回复
     *
     * @param commentReplyDto
     * @return
     */
    @Override
    public ResponseResult saveCommentReply(CommentReplyDto commentReplyDto) {
        if(commentReplyDto == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        User currentUser = userMapper.selectById(commentReplyDto.getUserId());
        if (currentUser == null){
            return ResponseResult.errorResult(500,"当前用户不存在");
        }
        CommentReply commentReply = new CommentReply();
        //设置回复的父评论id
        commentReply.setCommentId(commentReplyDto.getCommentId());
        //设置回复的用户的id
        commentReply.setUserId(commentReplyDto.getUserId());
        //设置回复的用户的用户名
        commentReply.setUserName(currentUser.getUsername());
        //设置回复的用户的头像
        commentReply.setProfilePhoto(currentUser.getProfilePhoto());
        //设置回复的内容
        commentReply.setContent(commentReplyDto.getContent());
        //设置回复时间
        commentReply.setCreatedTime(new Date());
        //默认设置为可见
        commentReply.setIsShow(CommonConstants.SHOW);
        Comment comment = commentMapper.selectById(commentReplyDto.getCommentId());
        if (comment == null){
            return ResponseResult.errorResult(500,"回复的评论不存在");
        }
        //设置被回复的用户的id
        commentReply.setReplyId(comment.getUserId());
        //设置被回复的用户的用户名
        commentReply.setReplyName(comment.getUserName());
        save(commentReply);
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    /**
     * 删除评论的回复
     * @param replyId
     * @return
     */
    @Override
    public ResponseResult deleteCommentReply(Integer replyId) {
        if(replyId == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        CommentReply byId = getById(replyId);
        byId.setIsShow(CommonConstants.DO_NOT_SHOW);
        updateById(byId);
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    /**
     * 点赞评论的回复
     * @param replyId
     * @return
     */
    @Override
    public ResponseResult likeCommentReply(Integer replyId) {
        if(replyId == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        CommentReply commentReply = getById(replyId);
        commentReply.setSumLike(commentReply.getSumLike() + 1);
        updateById(commentReply);
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    /**
     * 取消点赞或者点踩
     * @param replyId
     * @return
     */
    @Override
    public ResponseResult dislikeCommentReply(Integer replyId) {
        if(replyId == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        CommentReply commentReply = getById(replyId);
        commentReply.setSumLike(commentReply.getSumLike() - 1);
        updateById(commentReply);
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }
}
