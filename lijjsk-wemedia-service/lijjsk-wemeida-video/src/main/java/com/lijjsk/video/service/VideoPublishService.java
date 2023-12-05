package com.lijjsk.video.service;

import com.lijjsk.model.common.dtos.ResponseResult;

import java.util.Date;

public interface VideoPublishService {
    /**
     * 视频发布
     * @param id
     * @return
     */
    public ResponseResult publishVideo(Integer id);
}
