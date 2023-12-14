package com.lijjsk.statistics.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lijjsk.model.statistics.bos.VideoEvent;
import com.lijjsk.statistics.service.CommentProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CommentProcessorImpl implements CommentProcessor {
    @Autowired
    private UpdateRedisProcessorImpl updateRedisProcessorImpl;
    @Override
    @KafkaListener(topics = "add_comment_topic")
    public void addCommentNum(String jsonString) {
        ObjectMapper objectMapper = new ObjectMapper();
        // 将 JSON 字符串转换为对象
        try {
            VideoEvent videoEvent = objectMapper.readValue(jsonString, VideoEvent.class);
            updateRedisProcessorImpl.updateVideoData(videoEvent.getVideoId(),videoEvent.getType(),1);
        }catch (JsonProcessingException e){
            log.error("JSON转换失败");
            e.printStackTrace();
        }
    }

    @Override
    @KafkaListener(topics = "reduce_comment_topic")
    public void reduceCommentNum(String jsonString) {
        ObjectMapper objectMapper = new ObjectMapper();
        // 将 JSON 字符串转换为对象
        try {
            VideoEvent videoEvent = objectMapper.readValue(jsonString, VideoEvent.class);
            updateRedisProcessorImpl.updateVideoData(videoEvent.getVideoId(),videoEvent.getType(),0);
        }catch (JsonProcessingException e){
            log.error("JSON转换失败");
            e.printStackTrace();
        }
    }
}
