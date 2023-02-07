package com.example.examhomework.service;

import com.example.examhomework.model.dto.RegisterRequestDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

public interface UserService {

    ResponseEntity<?> registration(RegisterRequestDTO user, BindingResult validation);


}
