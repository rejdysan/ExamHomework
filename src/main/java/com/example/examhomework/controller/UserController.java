package com.example.examhomework.controller;

import com.example.examhomework.model.dto.RegisterRequestDTO;
import com.example.examhomework.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registration(@Valid @RequestBody RegisterRequestDTO user, BindingResult validation) {
        return userService.registration(user, validation);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody RegisterRequestDTO user, BindingResult validation) {
        return userService.login(user, validation);
    }

    @PutMapping("/topup")
    public ResponseEntity<?> topup(@RequestParam("amount") Long amount, @RequestHeader("authorization") String token) {
        return userService.topup(amount, token);
    }
}
