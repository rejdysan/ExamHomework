package com.example.examhomework.config;


import org.springframework.boot.context.properties.ConfigurationProperties;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@ConfigurationProperties(prefix = "rsa")
public record JWTRsaKeyProperties(RSAPublicKey publicKey, RSAPrivateKey privateKey) {
}

