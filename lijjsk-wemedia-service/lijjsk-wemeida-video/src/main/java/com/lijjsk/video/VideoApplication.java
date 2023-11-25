package com.lijjsk.video;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan({"com.lijjsk.video","com.minio.file"})
public class VideoApplication {
    public static void main(String []args){
        SpringApplication.run(VideoApplication.class,args);
    }
}
