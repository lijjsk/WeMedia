package com.lijjsk.video.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lijjsk.model.common.dtos.ResponseResult;
import com.lijjsk.model.wemedia.video.pojos.Video;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

public interface VideoUploadService extends IService<Video> {
    public ResponseResult uploadVideo(Integer userId,MultipartFile multipartFile);
    public ResponseResult uploadVideoInfo(MultipartFile image,Integer id,String title,String briefIntro);
}

