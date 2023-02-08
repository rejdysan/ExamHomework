package com.example.examhomework.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue
    private Long id;
    @Column(unique = true)
    private String username;
    private String password;
    private Long greenDollars = 50000L;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Sellable> sellableList;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Bid> bids;
    @OneToMany(mappedBy = "buyer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Sellable> boughtList;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
