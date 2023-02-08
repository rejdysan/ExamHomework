package com.example.examhomework.service;

import org.springframework.http.ResponseEntity;

public interface BidService {

    ResponseEntity<?> createBid(Long id, Long value, String token);
}
