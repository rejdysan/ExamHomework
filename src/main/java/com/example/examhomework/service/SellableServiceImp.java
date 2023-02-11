package com.example.examhomework.service;

import com.example.examhomework.model.Sellable;
import com.example.examhomework.model.User;
import com.example.examhomework.model.dto.*;
import com.example.examhomework.repository.SellableRepository;
import com.example.examhomework.repository.UserRepository;
import com.example.examhomework.util.TokenDecoder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.net.URL;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SellableServiceImp implements SellableService {

    private final SellableRepository sellableRepository;
    private final UserRepository userRepository;

    @Override
    public ResponseEntity<?> create(SellableRequestDTO sellable, BindingResult validation, String token) {
        if(validation.hasErrors()) return ResponseEntity.status(400).body(new ErrorDTO(validation.getAllErrors().get(0).getDefaultMessage()));
        try {
            new URL(sellable.getImageUrl()).toURI();
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new ErrorDTO("Invalid image_url"));
        }
        User user;
        try {
            user = userRepository.findById(TokenDecoder.decodeJWT(token).getId()).get();
        } catch (Exception e) {
            return ResponseEntity.status(401).body(new ErrorDTO("User does not exist or access denied due to invalid token"));
        }
        Sellable newSellable = sellableRepository.save(new Sellable(sellable, user));
        return ResponseEntity.status(200).body(new SellableResponseDTO(newSellable));
    }

    @Override
    public ResponseEntity<?> listPaginated(Integer page, String token) {
        User user;
        try {
            user = userRepository.findById(TokenDecoder.decodeJWT(token).getId()).get();
        } catch (Exception e) {
            return ResponseEntity.status(401).body(new ErrorDTO("User does not exist or access denied due to invalid token"));
        }
        if(page == null) page = 1;
        Pageable paging = PageRequest.of(page - 1, 20);
        List<SellableListResponseDTO> list = sellableRepository.findAll_ListDTO(paging);
        if(list.isEmpty()) return ResponseEntity.status(400).body(new ErrorDTO("No items found for requested page"));
        return ResponseEntity.status(200).body(list);
    }

    @Override
    public ResponseEntity<?> getSingleSellable(Long id, String token) {
        User user;
        try {
            user = userRepository.findById(TokenDecoder.decodeJWT(token).getId()).get();
        } catch (Exception e) {
            System.out.println("something added");
            return ResponseEntity.status(401).body(new ErrorDTO("User does not exist or access denied due to invalid token!"));
        }
        Sellable item;
        try {
            item = sellableRepository.findById(id).get();
        } catch (Exception e) {
            return ResponseEntity.status(404).body(new ErrorDTO("Item was not found"));
        }
        return ResponseEntity.status(200).body(new SellableSingleResponseDTO(item));
    }
}
