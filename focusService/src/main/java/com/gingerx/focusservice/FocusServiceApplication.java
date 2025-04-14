package com.gingerx.focusservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class FocusServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(FocusServiceApplication.class, args);
    }

}
