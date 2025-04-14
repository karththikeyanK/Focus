package com.gingerx.emailservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication(scanBasePackages = "com.gingerx.emailservice")
@EnableDiscoveryClient
public class EmailServiceApplication {


	public static void main(String[] args) {
		SpringApplication.run(EmailServiceApplication.class, args);
	}

}
