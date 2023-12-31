package com.lijjsk.video;

import com.lijjsk.video.mapper.VideoResolutionMapper;
import com.minio.file.service.FileStorageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = VideoApplication.class)
public class MyTest {
    @Autowired
    private FileStorageService fileStorageService;
    @Autowired
    private VideoResolutionMapper videoResolutionMapper;
    @Test
    public void test01(){
        System.out.println(fileStorageService.builderFilePathType("1", "11111","test.mp4","video"));
    }
    @Test
    public void test02(){
        System.out.println(videoResolutionMapper.getVideoResolution("19", "1080p60hz"));
    }
}
