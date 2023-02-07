package com.example.examhomework.model.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@NoArgsConstructor
public class RegisterRequestDTO {
    @Length(min = 5, message = "Username must contain at least 5 characters")
    @NotNull(message = "Username missing from request body")
    private String username;
    @Length(min = 8, message = "Password must contain at least 8 characters")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*\\p{Punct}).*$", message = "Password must contain at least one uppercase, one lowercase, one digit, one special character.")
    @NotNull(message = "Password missing from request body")
    private String password;
}
