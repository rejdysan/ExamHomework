package com.example.examhomework;

import com.example.examhomework.config.JWTRsaKeyProperties;
import com.example.examhomework.model.User;
import com.example.examhomework.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@EnableConfigurationProperties(JWTRsaKeyProperties.class)
@RequiredArgsConstructor
public class ExamHomeworkApplication implements CommandLineRunner {

    UserRepository userRepository;
    BCryptPasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(ExamHomeworkApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        if(!userRepository.existsById(1L)) {
            userRepository.save(new User("rejdysan", passwordEncoder.encode("Password1!")));
            userRepository.save(new User("misko", passwordEncoder.encode("Password1!")));
        }
    }
}
