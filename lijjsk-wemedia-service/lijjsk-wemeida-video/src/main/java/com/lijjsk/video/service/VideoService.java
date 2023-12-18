package com.lijjsk.video.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lijjsk.model.common.dtos.ResponseResult;
import com.lijjsk.model.wemedia.video.pojos.Video;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;

public interface VideoService extends IService<Video> {
    /**
     * 返回视频列表
     * @return
     */
    public ResponseResult getVideoList();


    /**
     * 获取用户的视频列表
     * @param userId
     * @return
     */
    public ResponseResult getUserVideoList(Integer userId);
    /**
     * 返回视频信息
     * @param videoId
     * @return
     */

    public ResponseResult getVideoInfo(Integer videoId);

    /**
     * 删除视频
     * @param videoId
     * @return
     */
    public ResponseResult deleteVideo(Integer videoId);
}
