package com.codingbox.tripjava;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.core.StreamWriteConstraints;

@Configuration
public class JacksonConfig {
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        // 최대 깊이 제한 늘리기
        mapper.getFactory().setStreamWriteConstraints(
            StreamWriteConstraints.builder()
                .maxNestingDepth(5000)  // 기본값 1000에서 2000으로 증가
                .build()
        );
        return mapper;
    }
}