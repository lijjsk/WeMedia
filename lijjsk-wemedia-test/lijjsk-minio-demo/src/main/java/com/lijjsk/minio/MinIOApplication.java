package com.lijjsk.minio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class MinIOApplication {
    public static void main(String[] args) {
        SpringApplication.run(MinIOApplication.class,args);
    }
}
