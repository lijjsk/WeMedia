package com.lijjsk.apis.video;

import com.lijjsk.apis.config.FeignConfig;
import com.lijjsk.model.common.dtos.ResponseResult;
import com.lijjsk.model.statistics.bos.VideoData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "wemedia-video",configuration = FeignConfig.class)
public interface IVideoClient {
    /**
     * 添加延迟任务
     * @return
     */
    @PostMapping("/api/v1/video/update")
    public boolean updateVideoData();
}