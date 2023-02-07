package com.example.examhomework;

import com.example.examhomework.config.JWTRsaKeyProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(JWTRsaKeyProperties.class)
public class ExamHomeworkApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExamHomeworkApplication.class, args);
    }

}
