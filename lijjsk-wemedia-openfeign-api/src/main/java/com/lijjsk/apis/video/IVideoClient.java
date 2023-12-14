package com.lijjsk.apis.video;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.lijjsk.model.common.dtos.ResponseResult;
import com.lijjsk.model.statistics.bos.VideoData;
import com.lijjsk.model.statistics.bos.VideoEvent;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("wemedia-video")
public interface IVideoClient {
    /**
     * 增加弹幕数量
     * @param videoId
     * @return
     */
    @PostMapping("/api/v1/video/add/barrage/{videoId}")
    public ResponseResult addVideoBarrageNum(@PathVariable("videoId") Integer videoId);
    /**
     * 增加视频播放量
     * @param videoId
     * @return
     */
    @PostMapping("/api/v1/video/add/view/{videoId}")
    public ResponseResult addVideoViewNum(@PathVariable("videoId") Integer videoId);
    /**
     * 增加视频点赞量
     * @param videoId
     * @return
     */
    @PostMapping("/api/v1/video/add/like/{videoId}")
    public ResponseResult addVideoLikeNum(@PathVariable("videoId") Integer videoId);
    /**
     * 增加视频投币量
     *
     * @param videoId
     * @return
     */
    @PostMapping("/api/v1/video/add/coin/{videoId}")
    public ResponseResult addVideoCoinNum(@PathVariable("videoId") Integer videoId);
    /**
     * 增加视频收藏量
     *
     * @param videoId
     * @return
     */
    @PostMapping("/api/v1/video/add/collect/{videoId}")
    public ResponseResult addVideoCollectNum(@PathVariable("videoId") Integer videoId);
    /**
     * 增加视频分享量
     *
     * @param videoId
     * @return
     */
    @PostMapping("/api/v1/video/add/share/{videoId}")
    public ResponseResult addVideoShareNum(@PathVariable("videoId") Integer videoId);

    /**
     * 更新视频数据
     * @param videoData
     * @return
     */
    @PostMapping("/api/v1/video/update/barrage")
    public ResponseResult updateVideoData(@RequestBody VideoData videoData);
}
