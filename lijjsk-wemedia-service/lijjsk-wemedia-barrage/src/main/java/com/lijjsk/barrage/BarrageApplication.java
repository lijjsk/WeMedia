package com.lijjsk.barrage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.lijjsk.apis")
public class BarrageApplication {
    public static void main(String[] args) {
        SpringApplication.run(BarrageApplication.class,args);
    }
}
