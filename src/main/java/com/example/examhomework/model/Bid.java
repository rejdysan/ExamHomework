package com.example.examhomework.model;

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
public class Bid {
    @Id
    @GeneratedValue
    private Long id;
    private Long value;
    @ManyToOne
    private Sellable item;
    @ManyToOne
    private User user;
}
