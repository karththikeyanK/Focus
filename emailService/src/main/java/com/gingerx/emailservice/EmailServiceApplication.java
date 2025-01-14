package com.gingerx.emailservice;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.core.env.Environment;

@SpringBootApplication
@EnableDiscoveryClient
@RequiredArgsConstructor
public class EmailServiceApplication {

	private final Environment environment;

	@Value("${spring.mail.username}")
	private static String emailUsername;

	public static void main(String[] args) {
		SpringApplication.run(EmailServiceApplication.class, args);
		System.out.println("Email User Name: " + emailUsername);
	}

}
