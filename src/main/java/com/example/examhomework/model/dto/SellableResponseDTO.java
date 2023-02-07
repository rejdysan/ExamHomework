package com.example.examhomework.model.dto;

import com.example.examhomework.model.Sellable;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class SellableResponseDTO {

    private Long id;
    private String name;
    private String description;
    @JsonProperty(value = "image_url")
    private String imageUrl;
    @JsonProperty(value = "starting_price")
    private Long startingPrice;
    @JsonProperty(value = "purchase_price")
    private Long purchasePrice;
    private String user;

    public SellableResponseDTO(Sellable sellable) {
        this.id = sellable.getId();
        this.name = sellable.getName();
        this.description = sellable.getDescription();
        this.imageUrl = sellable.getImageUrl();
        this.startingPrice = sellable.getStartingPrice();
        this.purchasePrice = sellable.getPurchasePrice();
        this.user = sellable.getUser().getUsername();
    }
}