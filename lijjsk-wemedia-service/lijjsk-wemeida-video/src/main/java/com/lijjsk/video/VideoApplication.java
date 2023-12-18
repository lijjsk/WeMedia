package com.lijjsk.video;

import io.seata.spring.annotation.GlobalTransactionScanner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan({"com.lijjsk.video","com.minio.file"})
@EnableFeignClients(basePackages = "com.lijjsk.apis")
@EnableAsync//开启异步调用
@EnableScheduling   //开启调度任务
public class VideoApplication {
    public static void main(String []args){
        SpringApplication.run(VideoApplication.class,args);
    }
    @Bean
    public GlobalTransactionScanner globalTransactionScanner() {
        return new GlobalTransactionScanner("video-service", "wemedia-video");
    }
}