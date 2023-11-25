package com.lijjsk.minio.controller;

import com.minio.file.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/v1/wemedia/video")
@CrossOrigin("*")
public class controller {

    @Autowired
    private FileStorageService fileStorageService;
    @GetMapping("/upload/test")
    public String test(){
        return "test";
    }
}