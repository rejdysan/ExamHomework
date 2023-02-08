package com.example.examhomework.model.dto;

import com.example.examhomework.model.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RegisterResponseDTO {
    private Long id;
    private String username;
    @JsonProperty(value = "green_dollars")
    private Long greenDollars;

    public RegisterResponseDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.greenDollars = user.getGreenDollars();
    }
}
