package com.lijjsk.video.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lijjsk.model.common.dtos.ResponseResult;
import com.lijjsk.model.statistics.bos.VideoData;
import com.lijjsk.model.wemedia.video.pojos.Video;
import org.springframework.web.bind.annotation.GetMapping;

public interface VideoBrowseService extends IService<Video> {
    /**
     * 增加视频弹幕量
     * @param videoId
     * @return
     */
    public ResponseResult addVideoBarrageNum(Integer videoId);
    /**
     * 增加视频播放量
     * @param videoId
     * @return
     */
    public ResponseResult addVideoViewNum(Integer videoId);
    /**
     * 增加视频点赞量
     * @param videoId
     * @return
     */
    public ResponseResult addVideoLikeNum(Integer videoId);
    /**
     * 增加视频投币量
     * @param videoId
     * @return
     */
    public ResponseResult addVideoCoinNum(Integer videoId);
    /**
     * 增加视频收藏量
     * @param videoId
     * @return
     */
    public ResponseResult addVideoCollectNum(Integer videoId);
    /**
     * 增加视频分享量
     * @param videoId
     * @return
     */
    public ResponseResult addVideoShareNum(Integer videoId);

    /**
     * 更新视频数据
     * @param videoData
     * @return
     */
    public ResponseResult updateVideoData(VideoData videoData);
}
