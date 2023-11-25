package com.lijjsk.video.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lijjsk.model.common.dtos.ResponseResult;
import com.lijjsk.model.wemedia.video.dtos.VideoDto;
import com.lijjsk.model.wemedia.video.pojos.Video;
import org.springframework.web.multipart.MultipartFile;

public interface VideoService extends IService<Video> {
    public ResponseResult uploadVideo(MultipartFile multipartFile);
    public ResponseResult uploadVideoInfo(MultipartFile image,Integer id,String title,String briefIntro);
}

