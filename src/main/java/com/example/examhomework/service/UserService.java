package com.example.examhomework.service;

import com.example.examhomework.model.dto.RegisterRequestDTO;
import org.springframework.http.ResponseEntity;

public interface UserService {

    ResponseEntity<?> registration(RegisterRequestDTO user);


}
