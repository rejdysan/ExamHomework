package com.example.examhomework.service;

import com.example.examhomework.model.Bid;
import com.example.examhomework.model.Sellable;
import com.example.examhomework.model.User;
import com.example.examhomework.model.dto.ErrorDTO;
import com.example.examhomework.model.dto.SellableSingleResponseDTO;
import com.example.examhomework.repository.BidRepository;
import com.example.examhomework.repository.SellableRepository;
import com.example.examhomework.repository.UserRepository;
import com.example.examhomework.util.TokenDecoder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BidServiceImp implements BidService {

    private final SellableRepository sellableRepository;
    private final BidRepository bidRepository;
    private final UserRepository userRepository;

    @Override
    public ResponseEntity<?> createBid(Long id, Long value, String token) {
        User user;
        try {
            user = userRepository.findById(TokenDecoder.decodeJWT(token).getId()).get();
        } catch (Exception e) {
            return ResponseEntity.status(401).body(new ErrorDTO("User does not exist or access denied due to invalid token"));
        }
        System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        Sellable item;
        try {
            item = sellableRepository.findById(id).get();
        } catch (Exception e) {
            return ResponseEntity.status(404).body(new ErrorDTO("Item not found"));
        }
        System.out.println("BBBBBBBBBBBBBBBBBBBBBBBBBBBBBB");
        if(!item.isSellable()) return ResponseEntity.status(400).body(new ErrorDTO("Item is not for sale"));
        if(user == item.getUser()) return ResponseEntity.status(400).body(new ErrorDTO("Cannot bid on your own item"));
        if(user.getGreenDollars() < value) return ResponseEntity.status(400).body(new ErrorDTO("Not enough green dollars for the bid"));
        if(value <= item.getStartingPrice()) return ResponseEntity.status(400).body(new ErrorDTO("Bid is equal or lower as starting price"));
        if(!item.getBids().isEmpty() && value <= item.getBids().get(item.getBids().size() - 1).getAmount()) return ResponseEntity.status(400).body(new ErrorDTO("Bid is too low for the highest bid"));
        Bid newBid = new Bid(value, item, user);
        if(value > item.getPurchasePrice()) {
            item.setBuyer(user);
            item.setPurchasePrice(value);
            item.setSellable(false);
        }
        item.addBid(newBid);
        user.setGreenDollars(user.getGreenDollars() - value);
        bidRepository.save(newBid);
        sellableRepository.save(item);
        userRepository.save(user);
        return ResponseEntity.status(200).body(new SellableSingleResponseDTO(item));
    }
}
