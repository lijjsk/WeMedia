package com.lijjsk.comment.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lijjsk.comment.mapper.CommentMapper;
import com.lijjsk.comment.mapper.UserMapper;
import com.lijjsk.comment.service.CommentService;
import com.lijjsk.common.constants.CommonConstants;
import com.lijjsk.common.constants.EventConstants;
import com.lijjsk.model.common.dtos.ResponseResult;
import com.lijjsk.model.common.enums.AppHttpCodeEnum;
import com.lijjsk.model.statistics.bos.VideoEvent;
import com.lijjsk.model.wemedia.comment.dtos.CommentDto;
import com.lijjsk.model.wemedia.comment.pojos.Comment;
import com.lijjsk.model.wemedia.user.pojos.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    /**
     * 保存评论
     * @param commentDto
     * @return
     */
    @Override
    public ResponseResult saveComment(CommentDto commentDto) {
        if (commentDto == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }else if(commentDto.getContent() == null){
            return ResponseResult.errorResult(500,"评论不能为空");
        }
        Comment comment = new Comment();
        BeanUtils.copyProperties(commentDto,comment);
        User user = userMapper.selectById(comment.getUserId());
        comment.setUserName(user.getUsername());
        comment.setProfilePhoto(user.getProfilePhoto());
        comment.setCreatedTime(new Date());
        //设置当前评论为可见
        comment.setIsShow(CommonConstants.SHOW);
        save(comment);
        //增加视频的评论数
        VideoEvent videoEvent = new VideoEvent();
        videoEvent.setVideoId(comment.getVideoId());
        videoEvent.setCreatedTime(new Date());
        videoEvent.setType(EventConstants.COMMENT);
        ObjectMapper objectMapper = new ObjectMapper();
        try{
            kafkaTemplate.send("add_barrage_topic",objectMapper.writeValueAsString(videoEvent));
        }catch (JsonProcessingException e){
            log.error("增加评论消息发送失败");
            e.printStackTrace();
        }
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    /**
     * 删除评论
     * @param commentId
     * @return
     */
    @Override
    public ResponseResult deleteComment(Integer commentId) {
        if (commentId == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        Comment comment = getById(commentId);
        //设置当前弹幕为不可见
        comment.setIsShow(CommonConstants.DO_NOT_SHOW);
        updateById(comment);
        //减少视频的评论数
        VideoEvent videoEvent = new VideoEvent();
        videoEvent.setVideoId(comment.getVideoId());
        videoEvent.setCreatedTime(new Date());
        videoEvent.setType(EventConstants.COMMENT);
        ObjectMapper objectMapper = new ObjectMapper();
        try{
            kafkaTemplate.send("reduce_comment_topic",objectMapper.writeValueAsString(videoEvent));
        }catch (JsonProcessingException e){
            log.error("减少评论消息发送失败");
            e.printStackTrace();
        }
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    /**
     * 获取评论列表
     *
     * @param videoId
     * @return
     */
    @Override
    public ResponseResult getCommentList(Integer videoId) {
        if (videoId == null){
            return ResponseResult.okResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        List<Comment> commentList = commentMapper.getCommentListByVideoId(videoId);
        return ResponseResult.okResult(commentList);
    }

    /**
     * 点赞评论
     *
     * @param commentId
     * @return
     */
    @Override
    public ResponseResult likeComment(Integer commentId) {
        if (commentId == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        Comment comment = getById(commentId);
        comment.setSumLike(comment.getSumLike()+1);
        updateById(comment);
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    /**
     * 取消点赞，或者点踩
     * @param commentId
     * @return
     */
    @Override
    public ResponseResult dislikeComment(Integer commentId) {
        if (commentId == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        Comment comment = getById(commentId);
        comment.setSumLike(comment.getSumLike()-1);
        updateById(comment);
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }
}
