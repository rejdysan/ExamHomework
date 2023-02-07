package com.example.examhomework.service;

import com.example.examhomework.model.dto.RegisterRequestDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImp implements UserService{
    @Override
    public ResponseEntity<?> registration(RegisterRequestDTO user) {
        return null;
    }
}
