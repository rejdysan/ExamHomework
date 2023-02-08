package com.example.examhomework.controller;

import com.example.examhomework.service.BidService;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Validated
public class BidController {

    private final BidService bidService;

    @PostMapping("/sellable/{id}/bid")
    public ResponseEntity<?> bid(
        @Min(value = 1, message = "Bid value must be larger than 0")
        @RequestParam(name = "value") Long value,
        @Min(value = 1, message = "Item ID must be positive integer larger than 0")
        @PathVariable("id") Long id,
        @RequestHeader("authorization") String token
    ) {
        return bidService.createBid(id, value, token);
    }
}
