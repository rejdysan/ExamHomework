package com.example.examhomework.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.stream.Collectors;

@Service
public class JWTService {
    private final JwtEncoder jwtEncoder;
    private final RefreshTokenService refreshTokenService;
    private final int accessTokenTime = Integer.parseInt(System.getenv("ACCESS_TIME"));
    private final int refreshTokenTime = Integer.parseInt(System.getenv("REFRESH_TIME"));

    public JWTService(JwtEncoder jwtEncoder, RefreshTokenService refreshTokenService) {
        this.jwtEncoder = jwtEncoder;
        this.refreshTokenService = refreshTokenService;
    }

    public String generateToken(Authentication authentication) {
        FoxBuyUserDetails tempUser = (FoxBuyUserDetails) authentication.getPrincipal();
        String scope = authentication.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.joining(" "));
        JwtClaimsSet payload = JwtClaimsSet.builder()
            .claim("username", authentication.getName())
            .claim("id", tempUser.getId())
            .claim("scope", scope)
            .expiresAt(new Date(System.currentTimeMillis() + accessTokenTime).toInstant())
            .build();
        return this.jwtEncoder.encode(JwtEncoderParameters.from(payload)).getTokenValue();
    }
}
