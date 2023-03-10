package com.example.examhomework.model.dto;

import com.example.examhomework.model.Bid;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BidDTO {
    private Long amount;
    private String username;

    public BidDTO(Bid bid) {
        this.amount = bid.getAmount();
        this.username = bid.getUser().getUsername();
    }
}
