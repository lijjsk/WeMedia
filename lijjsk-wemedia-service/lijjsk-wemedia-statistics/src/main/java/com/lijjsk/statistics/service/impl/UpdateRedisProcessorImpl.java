package com.lijjsk.statistics.service.impl;

import com.lijjsk.statistics.service.UpdateRedisProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UpdateRedisProcessorImpl implements UpdateRedisProcessor {
    private static final String VIDEO_HASH_KEY = "video_data";
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    public void updateVideoData(Integer videoId, String property,Integer type) {
        // 构造Redis中的键
        String hashKey = VIDEO_HASH_KEY + ":" + videoId;

        // 获取 Hash 操作对象
        HashOperations<String, String, Object> hashOperations = redisTemplate.opsForHash();

        // 从 Redis 中获取当前视频的数据
        Map<String, Object> videoData = hashOperations.entries(hashKey);

        // 如果当前视频数据不存在，则创建一个新的视频数据对象
        if (videoData == null) {
            videoData = new HashMap<>();
        }

        // 获取当前属性的值
        Integer currentValue = (Integer) videoData.getOrDefault(property, 0);
        if (type == 1){
            // 增加属性值
            currentValue++;
        }else {
            currentValue--;
        }


        // 将更新后的值存回视频数据中
        videoData.put(property, currentValue);

        // 将视频数据存回 Redis
        hashOperations.putAll(hashKey, videoData);
    }
}
