package com.asd.backened.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Cross domain configuration
 * @author Xuhao Guo and Yongyan Liu
 * @date 2022
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")	// Paths to allow cross domain access
        .allowedOrigins("*")	// Sources that allow cross domain access
        .allowedMethods("POST", "GET", "PUT", "OPTIONS", "DELETE")	// Allow Request Method
        .maxAge(168000)	// First check the minimum interval
        .allowedHeaders("*")  // Allow head settings
        .allowCredentials(true);	//  send cookies
    }
}