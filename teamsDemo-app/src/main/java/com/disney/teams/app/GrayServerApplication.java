package com.disney.teams.app;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;

@EnableDubbo
@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
public class GrayServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(GrayServerApplication.class, args);
    }
}
