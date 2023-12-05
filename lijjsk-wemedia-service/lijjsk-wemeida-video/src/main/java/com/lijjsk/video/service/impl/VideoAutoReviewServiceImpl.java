package com.lijjsk.video.service.impl;

import com.lijjsk.common.constants.VideoConstants;
import com.lijjsk.model.common.dtos.ResponseResult;
import com.lijjsk.model.wemedia.video.pojos.Video;
import com.lijjsk.video.mapper.VideoMapper;
import com.lijjsk.video.service.VideoAutoReviewService;
import com.lijjsk.video.service.VideoTaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class VideoAutoReviewServiceImpl implements VideoAutoReviewService {
    @Autowired
    private VideoMapper videoMapper;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Override
    public ResponseResult AutomaticReview(Integer id) {
        Video video = videoMapper.selectById(id);
        video.setStatus(VideoConstants.REVIEW_SUCCESS);
        video.setReason("审核成功");
        videoMapper.updateById(video);
        // 审核完成后，发布新任务到队列
        rabbitTemplate.convertAndSend("video-compress-queue", video.getId());
        return ResponseResult.okResult(video);
    }
}
