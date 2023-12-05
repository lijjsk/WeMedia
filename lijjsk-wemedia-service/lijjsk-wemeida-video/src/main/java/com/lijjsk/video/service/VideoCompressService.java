package com.lijjsk.video.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lijjsk.model.common.dtos.ResponseResult;
import com.lijjsk.model.wemedia.video.pojos.Video;
import com.lijjsk.model.wemedia.video.pojos.VideoResolution;

public interface VideoCompressService extends IService<VideoResolution> {
    /**
     * 视频压缩
     * @param id
     */
    public ResponseResult compressVideo(Integer id);
}
