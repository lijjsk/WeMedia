package com.lijjsk.video.feign;

import com.lijjsk.apis.video.IVideoClient;
import com.lijjsk.model.common.dtos.ResponseResult;
import com.lijjsk.video.service.VideoBrowseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VideoClient implements IVideoClient {
    @Autowired
    private VideoBrowseService videoBrowseService;

    /**
     * 增加弹幕数量
     * @param videoId
     * @return
     */
    @PostMapping("/api/v1/video/add/barrage/{videoId}")
    public ResponseResult addVideoBarrageNum(@PathVariable("videoId") Integer videoId) {
        return videoBrowseService.addVideoBarrageNum(videoId);
    }

    /**
     * 增加视频播放量
     *
     * @param videoId
     * @return
     */
    @PostMapping("/api/v1/video/add/view/{videoId}")
    public ResponseResult addVideoViewNum(@PathVariable("videoId") Integer videoId) {
        return videoBrowseService.addVideoViewNum(videoId);
    }

    /**
     * 增加视频点赞量
     *
     * @param videoId
     * @return
     */
    @PostMapping("/api/v1/video/add/like/{videoId}")
    public ResponseResult addVideoLikeNum(@PathVariable("videoId") Integer videoId) {
        return videoBrowseService.addVideoLikeNum(videoId);
    }

    /**
     * 增加视频投币量
     *
     * @param videoId
     * @return
     */
    @PostMapping("/api/v1/video/add/coin/{videoId}")
    public ResponseResult addVideoCoinNum(@PathVariable("videoId") Integer videoId) {
        return videoBrowseService.addVideoCoinNum(videoId);
    }

    /**
     * 增加视频收藏量
     *
     * @param videoId
     * @return
     */
    @PostMapping("/api/v1/video/add/collect/{videoId}")
    public ResponseResult addVideoCollectNum(@PathVariable("videoId") Integer videoId) {
        return videoBrowseService.addVideoCollectNum(videoId);
    }

    /**
     * 增加视频分享量
     *
     * @param videoId
     * @return
     */
    @PostMapping("/api/v1/video/add/share/{videoId}")
    public ResponseResult addVideoShareNum(@PathVariable("videoId") Integer videoId) {
        return videoBrowseService.addVideoShareNum(videoId);
    }
}
