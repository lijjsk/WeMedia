package com.lijjsk.statistics.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lijjsk.model.statistics.bos.VideoEvent;
import com.lijjsk.statistics.service.ViewProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ViewProcessorImpl implements ViewProcessor {
    @Autowired
    private UpdateRedisProcessorImpl updateRedisProcessorImpl;
    @Override
    @KafkaListener(topics = "add_view_topic")
    public void addViewNum(String jsonString) {
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
    @KafkaListener(topics = "reduce_view_topic")
    public void reduceViewNum(String jsonString) {
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
    @KafkaListener(topics = "add_like_topic")
    public void addLikeNum(String jsonString) {
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
    @KafkaListener(topics = "reduce_like_topic")
    public void reduceLikeNum(String jsonString) {
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
    @KafkaListener(topics = "add_collect_topic")
    public void addCollectNum(String jsonString) {
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
    @KafkaListener(topics = "reduce_collect_topic")
    public void reduceCollectNum(String jsonString) {
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
    @KafkaListener(topics = "add_share_topic")
    public void addShareNum(String jsonString) {
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
    @KafkaListener(topics = "reduce_share_topic")
    public void reduceShareNum(String jsonString) {
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
    @KafkaListener(topics = "add_coin_topic")
    public void addCoinNum(String jsonString) {
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
    @KafkaListener(topics = "reduce_coin_topic")
    public void reduceCoinNum(String jsonString) {
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
}
