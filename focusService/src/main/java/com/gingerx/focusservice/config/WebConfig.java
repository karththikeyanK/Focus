//package com.gingerx.focusservice.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//public class WebConfig implements WebMvcConfigurer {
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/swagger-ui/**")
//                .allowedOrigins("http://localhost:8080")
//                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
//                .allowedHeaders("*")
//                .allowCredentials(true);
//        registry.addMapping("/v3/api-docs/**")
//                .allowedOrigins("http://localhost:8080")
//                .allowedMethods("GET")
//                .allowedHeaders("*");
//        registry.addMapping("/swagger-resources/**")
//                .allowedOrigins("http://localhost:8080")
//                .allowedMethods("GET")
//                .allowedHeaders("*");
//        registry.addMapping("/webjars/**")
//                .allowedOrigins("http://localhost:8080")
//                .allowedMethods("GET");
//    }
//
//}
//
