package com.lijjsk.video.feign;

import com.alibaba.fastjson.JSON;
import com.lijjsk.apis.video.IVideoClient;
import com.lijjsk.model.common.dtos.ResponseResult;
import com.lijjsk.model.common.enums.AppHttpCodeEnum;
import com.lijjsk.model.statistics.bos.VideoData;
import com.lijjsk.video.service.VideoBrowseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VideoClient implements IVideoClient {
    @Autowired
    private VideoBrowseService videoBrowseService;
    @PostMapping("/api/v1/update/video")
    public String updateVideoData(VideoData videoData) {
        return JSON.toJSONString(ResponseResult.okResult(AppHttpCodeEnum.SUCCESS));
    }
}
