package com.example.examhomework.service;

import com.example.examhomework.model.User;
import com.example.examhomework.model.dto.ErrorDTO;
import com.example.examhomework.model.dto.RegisterRequestDTO;
import com.example.examhomework.model.dto.RegisterResponseDTO;
import com.example.examhomework.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.List;
import java.util.Objects;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserServiceImpTest {
    @Autowired
    UserService userService;

    @MockBean
    UserRepository userRepository;

    @Mock
    BCryptPasswordEncoder bcrypt;

    @Mock
    BindingResult validation;

    RegisterRequestDTO request;

    @BeforeAll
    public void setUp() {
        request = new RegisterRequestDTO("john.doe", "password");
    }

    @Test
    public void testRegistrationSuccess() {
        when(validation.hasErrors()).thenReturn(false);
        when(bcrypt.encode(request.getPassword())).thenReturn("encrypted password");
        when(userRepository.save(any(User.class))).thenReturn(new User(1L, request.getUsername(), "encrypted password", 0L));

        ResponseEntity<?> result = userService.registration(request, validation);
        Assertions.assertEquals(200, result.getStatusCode().value());
        Assertions.assertEquals(request.getUsername(), ((RegisterResponseDTO) Objects.requireNonNull(result.getBody())).getUsername());
    }

    @Test
    public void testRegistrationFailureValidationErrors() {
        when(validation.hasErrors()).thenReturn(true);
        when(validation.getAllErrors()).thenReturn(List.of(new ObjectError("username", "Username is required")));

        ResponseEntity<?> result = userService.registration(request, validation);
        Assertions.assertEquals(400, result.getStatusCode().value());
        Assertions.assertEquals("Username is required", ((ErrorDTO) Objects.requireNonNull(result.getBody())).getMessage());
    }

    @Test
    public void testRegistrationFailureUserExists() {
        when(validation.hasErrors()).thenReturn(false);
        when(bcrypt.encode(request.getPassword())).thenReturn("encrypted password");
        when(userRepository.save(any(User.class))).thenThrow(new DataIntegrityViolationException(""));

        ResponseEntity<?> result = userService.registration(request, validation);
        Assertions.assertEquals(400, result.getStatusCode().value());
        Assertions.assertEquals("User already exists", ((ErrorDTO) Objects.requireNonNull(result.getBody())).getMessage());
    }
}