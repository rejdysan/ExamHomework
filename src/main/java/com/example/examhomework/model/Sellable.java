package com.example.examhomework.model;

import com.example.examhomework.model.dto.SellableRequestDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Sellable {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String description;
    private String imageUrl;
    private Long startingPrice;
    private Long purchasePrice;
    @ManyToOne
    private User user;

    public Sellable(SellableRequestDTO sellable, User user) {
        this.name = sellable.getName();
        this.description = sellable.getDescription();
        this.imageUrl = sellable.getImageUrl();
        this.startingPrice = sellable.getStartingPrice().longValue();
        this.purchasePrice = sellable.getPurchasePrice().longValue();
        this.user = user;
    }
}