package com.example.examhomework.config;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class JWTService {
    private final JwtEncoder jwtEncoder;

    public String generateToken(Authentication authentication) {
        MyUserDetails tempUser = (MyUserDetails) authentication.getPrincipal();
        JwtClaimsSet payload = JwtClaimsSet.builder()
            .claim("username", authentication.getName())
            .claim("id", tempUser.getId())
            .build();
        return this.jwtEncoder.encode(JwtEncoderParameters.from(payload)).getTokenValue();
    }
}
