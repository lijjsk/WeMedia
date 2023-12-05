package com.lijjsk.video.rabbit;

import com.lijjsk.video.service.VideoAutoReviewService;
import com.lijjsk.video.service.impl.VideoCompressServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class VideoReviewTaskHandler {
    @Autowired
    private VideoAutoReviewService videoAutoReviewService;

    @RabbitListener(queues = "video-review-queue")
    public void handleVideoProcessingTask(Integer videoId) {
        log.info("处理视频审核任务: {}", videoId);
        videoAutoReviewService.AutomaticReview(videoId);
    }
}
