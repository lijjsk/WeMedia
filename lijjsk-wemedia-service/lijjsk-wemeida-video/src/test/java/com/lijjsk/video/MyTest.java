package com.lijjsk.video;

import com.minio.file.service.FileStorageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = VideoApplication.class)
public class MyTest {
    @Autowired
    private FileStorageService fileStorageService;
    @Test
    public void test01(){
        System.out.println(fileStorageService.builderFilePathType("1", "11111","test.mp4","video"));

    }
}
