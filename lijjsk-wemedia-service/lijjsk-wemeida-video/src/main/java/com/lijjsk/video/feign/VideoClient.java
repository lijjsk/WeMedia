package com.lijjsk.video.feign;

import com.lijjsk.apis.video.IVideoClient;
import com.lijjsk.model.common.dtos.ResponseResult;
import com.lijjsk.model.statistics.bos.VideoData;
import com.lijjsk.video.service.VideoBrowseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class VideoClient implements IVideoClient {
    @Autowired
    private VideoBrowseService videoBrowseService;
    /**
     * 添加延迟任务
     * @return
     */
    @PostMapping("/api/v1/video/update")
    public boolean updateVideoData() {
        return true;
    }
}
