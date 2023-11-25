package com.lijjsk.video.controller;

import com.lijjsk.model.common.dtos.ResponseResult;
import com.lijjsk.video.service.VideoUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/v1/wemedia/video")
@CrossOrigin("*")
public class VideoController {
    @Autowired
    private VideoUploadService videoUploadService;
    @PostMapping("/upload/Video")
    public ResponseResult uploadVideo(@RequestParam("file") MultipartFile multipartFile){
        return videoUploadService.uploadVideo(multipartFile);
    }
    @PostMapping("/upload/VideoInfo")
    public ResponseResult uploadVideoInfo(@RequestPart MultipartFile imageFile,
                                          @RequestParam Integer id,
                                          @RequestParam String title,
                                          @RequestParam String briefIntro){
        return videoUploadService.uploadVideoInfo(imageFile,id,title,briefIntro);
    }

    @GetMapping("/upload/test")
    public String test(){

        return "test";
    }
}


