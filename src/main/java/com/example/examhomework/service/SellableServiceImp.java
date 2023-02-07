package com.example.examhomework.service;

import com.example.examhomework.model.Sellable;
import com.example.examhomework.model.User;
import com.example.examhomework.model.dto.ErrorDTO;
import com.example.examhomework.model.dto.RegisterResponseDTO;
import com.example.examhomework.model.dto.SellableRequestDTO;
import com.example.examhomework.model.dto.SellableResponseDTO;
import com.example.examhomework.repository.SellableRepository;
import com.example.examhomework.repository.UserRepository;
import com.example.examhomework.util.TokenDecoder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.net.URL;

@Service
@RequiredArgsConstructor
public class SellableServiceImp implements SellableService {

    private final SellableRepository sellableRepository;
    private final UserRepository userRepository;
    @Override
    public ResponseEntity<?> create(SellableRequestDTO sellable, BindingResult validation, String token) {
        if(validation.hasErrors()) {
            return ResponseEntity.status(400).body(new ErrorDTO(validation.getAllErrors().get(0).getDefaultMessage()));
        }
        try {
            new URL(sellable.getImageUrl()).toURI();
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new ErrorDTO("Invalid image_url"));
        }
        User user;
        try {
            user = userRepository.findById(TokenDecoder.decodeJWT(token).getId()).get();
        } catch (Exception e) {
            return ResponseEntity.status(401).body(new ErrorDTO("Access denied due to invalid token"));
        }
        Sellable newSellable = sellableRepository.save(new Sellable(sellable, user));
        return ResponseEntity.status(200).body(new SellableResponseDTO(newSellable));
    }
}
