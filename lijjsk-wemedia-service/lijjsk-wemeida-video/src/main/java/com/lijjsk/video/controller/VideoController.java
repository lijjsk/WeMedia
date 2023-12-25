package com.lijjsk.video.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.lijjsk.model.common.dtos.ResponseResult;
import com.lijjsk.model.common.enums.AppHttpCodeEnum;
import com.lijjsk.video.service.*;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/wemedia/video")
public class VideoController {
    @Autowired
    private VideoUploadService videoUploadService;
    @Autowired
    private VideoTaskService videoTaskService;
    @Autowired
    private VideoService videoService;
    @PostMapping("/upload/video")
    public ResponseResult uploadVideo(@RequestParam Integer userId,@RequestParam("file") MultipartFile multipartFile){
        return videoUploadService.uploadVideo(userId,multipartFile);
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
        videoTaskService.addVideoPublishToTask(videoId, publishTime);
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }
    @GetMapping("/get/videoList")
    @SentinelResource("getVideoList")
    public ResponseResult getVideoList(){
        return videoService.getVideoList();
    }
    @GetMapping("/get/user/videoList")
    public ResponseResult getUserVideoList(@RequestParam Integer userId){
        return videoService.getUserVideoList(userId);
    }
    @DeleteMapping("/delete/video")
    public ResponseResult deleteVideo(@RequestParam Integer videoId){
        return videoService.deleteVideo(videoId);
    }
    @GetMapping("/get/videoInfo")
    public ResponseResult getVideoInfo(@RequestParam Integer videoId){
        return videoService.getVideoInfo(videoId);
    }
    @PutMapping("/down/video")
    public ResponseResult downVideo(@RequestParam Integer videoId){
        return videoService.downVideo(videoId);
    }
}


