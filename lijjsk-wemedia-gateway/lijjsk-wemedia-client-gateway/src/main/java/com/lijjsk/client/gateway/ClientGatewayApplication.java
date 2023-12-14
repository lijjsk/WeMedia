package com.lijjsk.client.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ClientGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(ClientGatewayApplication.class,args);
    }
}
