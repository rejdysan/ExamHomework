package com.example.examhomework.service;

import com.example.examhomework.model.dto.SellableRequestDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

public interface SellableService {
    ResponseEntity<?> create(SellableRequestDTO sellable, BindingResult validation, String token);
}
