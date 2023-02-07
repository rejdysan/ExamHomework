package com.example.examhomework.model.dto;

import com.example.examhomework.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RegisterResponseDTO {
    private Long id;
    private String username;

    public RegisterResponseDTO(User user) {
        this.id = user.getId();
        this.username = getUsername();
    }
}
