package com.lijjsk.video.rabbit;

import com.lijjsk.video.service.impl.VideoCompressServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class VideoCompressTaskHandler {

    @Autowired
    private VideoCompressServiceImpl videoCompressService;

    @RabbitListener(queues = "video-compress-queue")
    public void handleVideoProcessingTask(Integer videoId) {
        log.info("处理视频压缩任务: {}", videoId);
        videoCompressService.compressVideo(videoId);
    }
}
