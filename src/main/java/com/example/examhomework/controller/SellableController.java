package com.example.examhomework.controller;

import com.example.examhomework.model.dto.SellableRequestDTO;
import com.example.examhomework.service.SellableService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Validated
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

    @GetMapping("/sellable")
    public ResponseEntity<?> sellablePage(
        @Min(value = 1, message = "Page number must be integer larger than 0")
        @RequestParam(name = "page", required = false) Integer page,
        @RequestHeader("authorization") String token
    ) {
        return sellableService.listPaginated(page, token);
    }

    @GetMapping("/sellable/{id}")
    public ResponseEntity<?> singleSellable(
        @Min(value = 1, message = "Sellable ID must be larger than 0")
        @PathVariable("id") Long id,
        @RequestHeader("authorization") String token
    ) {
        return sellableService.getSingleSellable(id, token);
    }
}
