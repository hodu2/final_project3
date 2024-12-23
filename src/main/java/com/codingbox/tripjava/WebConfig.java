package com.codingbox.tripjava;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 이미지를 외부 디렉토리에서 제공할 수 있도록 설정
        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:/D:/pbh_0710/spring/workspace/review_project/src/main/resources/static/images/");
    }
}