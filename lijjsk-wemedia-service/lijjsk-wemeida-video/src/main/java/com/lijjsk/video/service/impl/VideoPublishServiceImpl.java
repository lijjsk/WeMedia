package com.lijjsk.video.service.impl;

import com.lijjsk.common.constants.VideoConstants;
import com.lijjsk.model.common.dtos.ResponseResult;
import com.lijjsk.model.common.enums.AppHttpCodeEnum;
import com.lijjsk.model.wemedia.video.pojos.Video;
import com.lijjsk.video.mapper.VideoMapper;
import com.lijjsk.video.service.VideoPublishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class VideoPublishServiceImpl implements VideoPublishService {
    @Autowired
    private VideoMapper videoMapper;
    /**
     * 视频发布
     *
     * @param id
     * @return
     */
    @Override
    public ResponseResult publishVideo(Integer id) {
        Video video = videoMapper.selectById(id);
        video.setStatus(VideoConstants.PUBLISHED);
        videoMapper.updateById(video);
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }
}
