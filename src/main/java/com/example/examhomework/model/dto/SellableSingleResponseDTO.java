package com.example.examhomework.model.dto;

import com.example.examhomework.model.Sellable;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class SellableSingleResponseDTO {
    private String name;
    private String description;
    @JsonProperty(value = "image_url")
    private String imageUrl;
    private List<BidDTO> bids;
    @JsonProperty(value = "purchase_price")
    private Long purchasePrice;
    private String seller;
    private String buyer;

    public SellableSingleResponseDTO(Sellable sellable) {
        this.name = sellable.getName();
        this.description = sellable.getDescription();
        this.imageUrl = sellable.getImageUrl();
        this.bids = sellable.getBids().stream().map(BidDTO::new).toList();
        this.purchasePrice = sellable.getPurchasePrice();
        this.seller = sellable.getUser().getUsername();
        this.buyer = (sellable.getBuyer() == null) ? "NOT SOLD YET - auction still ongoing" : sellable.getBuyer().getUsername();
    }
}
