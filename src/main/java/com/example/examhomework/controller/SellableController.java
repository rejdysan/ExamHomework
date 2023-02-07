package com.example.examhomework.controller;

import com.example.examhomework.model.dto.SellableRequestDTO;
import com.example.examhomework.service.SellableService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class SellableController {

    private final SellableService sellableService;

    @PostMapping("/sellable")
    public ResponseEntity<?> create(
        @Valid @RequestBody SellableRequestDTO sellable,
        BindingResult validation,
        @RequestHeader("authorization") String token
    ) {
        return sellableService.create(sellable, validation, token);
    }
}
