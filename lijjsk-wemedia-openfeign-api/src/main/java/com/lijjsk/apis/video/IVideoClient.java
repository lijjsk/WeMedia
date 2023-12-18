package com.lijjsk.apis.video;

import com.lijjsk.apis.config.FeignConfig;
import com.lijjsk.model.common.dtos.ResponseResult;
import com.lijjsk.model.statistics.bos.VideoData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "wemedia-video",configuration = FeignConfig.class)
public interface IVideoClient {
    @PostMapping(value = "/api/v1/update/video", consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    public String updateVideoData(@RequestBody VideoData videoData);
}
