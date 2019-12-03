package com.aquamarine.barraiser.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.lang.reflect.Type;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final long MAX_AGE_SECS = 3600;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("HEAD", "OPTIONS", "GET", "POST", "PUT", "PATCH", "DELETE")
                .maxAge(MAX_AGE_SECS);
    }

//    @Component
//    public static class MultipartJackson2HttpMessageConverter extends AbstractJackson2HttpMessageConverter {
//
//        /**
//         * Converter for support http request with header Content-Type: multipart/form-data
//         */
//        public MultipartJackson2HttpMessageConverter(ObjectMapper objectMapper) {
//            super(objectMapper, MediaType.APPLICATION_OCTET_STREAM);
//        }
//
//        @Override
//        public boolean canWrite(Class<?> clazz, MediaType mediaType) {
//            return false;
//        }
//
//        @Override
//        public boolean canWrite(Type type, Class<?> clazz, MediaType mediaType) {
//            return false;
//        }
//
//        @Override
//        protected boolean canWrite(MediaType mediaType) {
//            return false;
//        }
//    }
}