package com.lijjsk.statistics.service.impl;

import com.lijjsk.apis.video.IVideoClient;
import com.lijjsk.model.statistics.bos.VideoData;
import com.lijjsk.statistics.service.RedisDataSyncTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;

@Component
@Service
@Slf4j
public class RedisDataSyncTaskImpl implements RedisDataSyncTask {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private IVideoClient videoClient;

    private static final String VIDEO_HASH_KEY = "video_data";

    @Scheduled(fixedRate = 60000) // 每隔60秒执行一次，根据需要进行调整
    public void syncDataToDatabase() {
        Set<String> videoKeys = redisTemplate.keys(VIDEO_HASH_KEY + ":*");

        if (videoKeys != null) {
            for (String key : videoKeys) {
                // 使用字符串分割获取 id 部分
                String[] parts = key.split(":");
                if (parts.length == 2) {
                    Integer videoId = Integer.parseInt(parts[1]);

                    // 获取视频数据
                    Map<Object, Object> videoDataMap = redisTemplate.opsForHash().entries(key);

                    // 将 id 放入 videoDataMap
                    videoDataMap.put("videoId", videoId);

                    // 更新视频数据
                    if (videoDataMap != null) {
                        VideoData videoData = convertToVideoData(videoDataMap);
                        log.info("调用视频微服务同步视频数据");
                        videoClient.updateVideoData(videoData);
                    }
                }
            }
        }
    }

    private VideoData convertToVideoData(Map<Object, Object> videoDataMap) {
        VideoData videoData = new VideoData();
        videoData.setVideoId((int) videoDataMap.get("videoId"));
        videoData.setSumLike((int) videoDataMap.getOrDefault("like", 0));
        videoData.setSumCoins((int) videoDataMap.getOrDefault("coin", 0));
        videoData.setSumCollect((int) videoDataMap.getOrDefault("collect", 0));
        videoData.setSumShare((int) videoDataMap.getOrDefault("share", 0));
        videoData.setSumView((int) videoDataMap.getOrDefault("views", 0));
        videoData.setSumDanMu((int) videoDataMap.getOrDefault("barrage", 0));
        videoData.setIncome(videoData.getSumView() * 0.002);
        return videoData;
    }
}