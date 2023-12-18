package com.lijjsk.advertising.controller;

import com.lijjsk.advertising.service.AdvertisementService;
import com.lijjsk.model.common.dtos.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/wemedia/advertising")
public class AdvertisingController {
    @Autowired
    private AdvertisementService advertisementService;

    @PostMapping("/add/advertisement")
    public ResponseResult addAdvertisement(@RequestParam("advertiserId") Integer advertiserId,
                                           @RequestParam("content") String content,
                                           @RequestParam("duration") Integer duration,
                                           @RequestParam("position") Integer position,
                                           @RequestParam("image")MultipartFile multipartFile){
        return advertisementService.addAdvertisement(advertiserId,content,duration,position,multipartFile);
    }

    @DeleteMapping("/stop/advertisement")
    public ResponseResult stopAdvertisement(@RequestParam("advertisementId") Integer advertisementId){
        return advertisementService.stopAdvertisement(advertisementId);
    }

    @GetMapping("/get/all/advertisement")
    public ResponseResult getAllAdvertisement(){
        return advertisementService.getAllAdvertisement();
    }

    @GetMapping("/get/user/advertisement")
    public ResponseResult getUserAdvertisement(@RequestParam("advertiserId") Integer advertiserId){
        return advertisementService.getUserAdvertisement(advertiserId);
    }
    @PutMapping("/update/advertisement/show")
    public ResponseResult showAdvertisement(@RequestParam("advertisementId") Integer advertisementId){
        return advertisementService.showAdvertisement(advertisementId);
    }
    @PutMapping("/update/advertisement/click")
    public ResponseResult clickAdvertisement(@RequestParam("advertisementId") Integer advertisementId){
        return advertisementService.clickAdvertisement(advertisementId);
    }

    @PutMapping("/update/advertisement/conversion")
    public ResponseResult converseAdvertisement(@RequestParam("advertisementId") Integer advertisementId){
        return advertisementService.converseAdvertisement(advertisementId);
    }
    @GetMapping("/get/advertisement/info")
    public ResponseResult getAdvertisementInfo(@RequestParam("advertisementId") Integer advertisementId){
        return advertisementService.getAdvertisementInfo(advertisementId);
    }
}
