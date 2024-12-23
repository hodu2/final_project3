package com.codingbox.tripjava;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import jakarta.servlet.MultipartConfigElement;

@Configuration
public class FileUploadConfig {
	
	   @Bean
	    public MultipartConfigElement multipartConfigElement() {
	        return new MultipartConfigElement("");
	    }
}
