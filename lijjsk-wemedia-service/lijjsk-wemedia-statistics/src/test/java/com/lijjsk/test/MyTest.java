package com.lijjsk.test;

import com.lijjsk.apis.video.IVideoClient;
import com.lijjsk.model.statistics.bos.VideoData;
import com.lijjsk.statistics.StatisticsApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = StatisticsApplication.class)
public class MyTest {

    @Autowired
    private IVideoClient videoClient;
    @Test
    public void test1(){
        VideoData videoData = new VideoData();
        System.out.println(videoClient.updateVideoData());
    }
}
