package com.lijjsk.advertising;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan({"com.lijjsk.advertising","com.minio.file"})
@EnableScheduling
public class AdvertisingApplication {
    public static void main(String[] args) {
        SpringApplication.run(AdvertisingApplication.class,args);
    }
}
