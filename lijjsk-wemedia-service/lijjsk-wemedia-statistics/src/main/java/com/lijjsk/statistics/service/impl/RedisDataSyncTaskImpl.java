package com.lijjsk.statistics.service.impl;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.lijjsk.model.statistics.bos.VideoData;
import com.lijjsk.model.wemedia.video.pojos.Video;
import com.lijjsk.statistics.mapper.VideoMapper;
import com.lijjsk.statistics.service.RedisDataSyncTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;
import java.util.Set;

@Service
@Slf4j
public class RedisDataSyncTaskImpl implements RedisDataSyncTask {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    private static final String VIDEO_HASH_KEY = "video_data";
    @Autowired
    private VideoMapper videoMapper;

    @Scheduled(fixedRate = 1000 * 60) // 每隔10秒执行一次，根据需要进行调整
    @Transactional
    public void syncDataToDatabase() {
        log.info("同步视频数据");
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
                    Video video = videoMapper.selectById(videoId);
                    if (video == null){
                        log.error("当前视频不存在，id为:{}",videoId);
                    }else {
                        video.setSumLike((int) videoDataMap.getOrDefault("like", 0));
                        video.setSumCoins((int) videoDataMap.getOrDefault("coin", 0));
                        video.setSumCollect((int) videoDataMap.getOrDefault("collect", 0));
                        video.setSumShare((int) videoDataMap.getOrDefault("share", 0));
                        video.setSumView((int) videoDataMap.getOrDefault("views", 0));
                        video.setSumDanMu((int) videoDataMap.getOrDefault("barrage", 0));
                        video.setSumComment((int) videoDataMap.getOrDefault("comment",0));
                        video.setIncome(video.getSumView() * 0.002);
                        videoMapper.updateById(video);
                        log.info("当前视频更新成功，id为:{}",video.getId());
                    }
                }
            }
        }
        log.info("视频数据同步完成");
    }

//    private VideoData convertToVideoData(Map<Object, Object> videoDataMap) {
//        VideoData videoData = new VideoData();
//        videoData.setVideoId((int) videoDataMap.get("videoId"));
//        videoData.setSumLike((int) videoDataMap.getOrDefault("like", 0));
//        videoData.setSumCoins((int) videoDataMap.getOrDefault("coin", 0));
//        videoData.setSumCollect((int) videoDataMap.getOrDefault("collect", 0));
//        videoData.setSumShare((int) videoDataMap.getOrDefault("share", 0));
//        videoData.setSumView((int) videoDataMap.getOrDefault("views", 0));
//        videoData.setSumDanMu((int) videoDataMap.getOrDefault("barrage", 0));
//        videoData.setIncome(videoData.getSumView() * 0.002);
//        return videoData;
//    }
}