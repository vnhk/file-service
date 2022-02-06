package com.berluk.fileservice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@SpringBootApplication
public class FileServiceApplication {

    private static final int ONE_MB = 1048576;
    @Value("${file.service.upload.mb.max}")
    private Long MAX_SIZE;

    public static void main(String[] args) {
        SpringApplication.run(FileServiceApplication.class, args);
    }

    @Bean
    public MultipartResolver multipartResolver() {
        CommonsMultipartResolver multipartResolver
                = new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(ONE_MB * MAX_SIZE);
        return multipartResolver;
    }
}
