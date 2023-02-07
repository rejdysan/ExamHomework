package com.example.examhomework.config;

import com.example.examhomework.model.User;
import com.example.examhomework.model.dto.RegisterRequestDTO;
import com.example.examhomework.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registration(@Valid @RequestBody RegisterRequestDTO user, BindingResult validation) {
        return userService.registration(user, validation);
    }

}
