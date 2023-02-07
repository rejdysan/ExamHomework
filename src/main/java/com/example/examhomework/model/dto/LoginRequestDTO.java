package com.example.examhomework.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDTO {
    @NotBlank(message = "Field username and/or field password was empty!")
    @NotNull(message = "Field username and/or field password was null!")
    String username;
    @NotBlank(message = "Field username and/or field password was empty!")
    @NotNull(message = "Field username and/or field password was null!")
    String password;
}
