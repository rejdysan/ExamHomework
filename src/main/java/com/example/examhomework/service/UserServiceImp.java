package com.example.examhomework.service;

import com.example.examhomework.config.JWTService;
import com.example.examhomework.config.MyUserDetails;
import com.example.examhomework.model.User;
import com.example.examhomework.model.dto.ErrorDTO;
import com.example.examhomework.model.dto.LoginResponseDTO;
import com.example.examhomework.model.dto.RegisterRequestDTO;
import com.example.examhomework.model.dto.RegisterResponseDTO;
import com.example.examhomework.repository.UserRepository;
import com.example.examhomework.util.TokenDecoder;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bcrypt;
    private final JWTService tokenService;
    private final AuthenticationManager authManager;

    @Override
    public ResponseEntity<?> registration(@Valid @RequestBody RegisterRequestDTO user, BindingResult validation) {
        if(validation.hasErrors()) return ResponseEntity.status(400).body(new ErrorDTO(validation.getAllErrors().get(0).getDefaultMessage()));
        User newUser;
        try {
            newUser = userRepository.save(new User(user.getUsername(), bcrypt.encode(user.getPassword())));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new ErrorDTO("User already exists"));
        }
        return ResponseEntity.status(200).body(new RegisterResponseDTO(newUser));
    }

    @Override
    public ResponseEntity<?> login(@Valid @RequestBody RegisterRequestDTO user, BindingResult validation) {
        if(validation.hasErrors()) return ResponseEntity.status(400).body(new ErrorDTO(validation.getAllErrors().get(0).getDefaultMessage()));
        Authentication authentication;
        try {
            authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new ErrorDTO("Username or password incorrect"));
        }
        return ResponseEntity.status(200).body(new LoginResponseDTO(tokenService.generateToken(authentication), ((MyUserDetails) authentication.getPrincipal()).getGreenDollars()));
    }

    @Override
    public ResponseEntity<?> topup(Long amount, String token) {
        User user;
        try {
            user = userRepository.findById(TokenDecoder.decodeJWT(token).getId()).get();
        } catch (Exception e) {
            return ResponseEntity.status(401).body(new ErrorDTO("User does not exist or access denied due to invalid token"));
        }
        user.setGreenDollars(user.getGreenDollars() + amount);
        userRepository.save(user);
        return ResponseEntity.status(200).body(new RegisterResponseDTO(user));
    }
}
