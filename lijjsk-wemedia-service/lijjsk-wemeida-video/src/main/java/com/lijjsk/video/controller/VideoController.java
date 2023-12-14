package com.lijjsk.video.controller;

import com.lijjsk.model.common.dtos.ResponseResult;
import com.lijjsk.model.common.enums.AppHttpCodeEnum;
import com.lijjsk.video.service.VideoBrowseService;
import com.lijjsk.video.service.VideoPublishService;
import com.lijjsk.video.service.VideoTaskService;
import com.lijjsk.video.service.VideoUploadService;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@RestController
@RequestMapping("/v1/wemedia/video")
@CrossOrigin("*")
public class VideoController {
    @Autowired
    private VideoUploadService videoUploadService;
    @Autowired
    private VideoBrowseService videoBrowseService;
    @Autowired
    private VideoTaskService videoTaskService;
    @PostMapping("/upload/video")
    public ResponseResult uploadVideo(@RequestParam("file") MultipartFile multipartFile){
        return videoUploadService.uploadVideo(multipartFile);
    }
    @PostMapping("/upload/videoInfo")
    public ResponseResult uploadVideoInfo(@RequestPart MultipartFile imageFile,
                                          @RequestParam Integer id,
                                          @RequestParam String title,
                                          @RequestParam String briefIntro){
        return videoUploadService.uploadVideoInfo(imageFile,id,title,briefIntro);
    }
    @PostMapping("/publish/video")
    public ResponseResult publishVideo(@RequestParam("videoId") Integer videoId,
                                       @RequestParam("publishTime") Date publishTime){
        videoTaskService.addVideoPublishToTask(videoId,publishTime);
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }
    @GetMapping("/get/videoList")
    public ResponseResult getVideoList(){
        return videoBrowseService.getVideoList();
    }
    @GetMapping("/get/videoInfo")
    public ResponseResult getVideoInfo(@RequestParam Integer id){
        return videoBrowseService.getVideoInfo(id);
    }
    @GetMapping("/upload/test")
    public String test(){
        return "test";
    }
}


