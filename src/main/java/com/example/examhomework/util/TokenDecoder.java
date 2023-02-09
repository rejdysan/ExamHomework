package com.example.examhomework.util;

import com.nimbusds.jose.shaded.gson.Gson;
import lombok.Getter;
import lombok.Setter;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
@Getter
@Setter
public class TokenDecoder {

    private Long id;
    private String username;

    public static TokenDecoder decodeJWT(String token) {
        String b64payload = token.split("\\.")[1];
        String jsonString = new String(Base64.decodeBase64(b64payload), StandardCharsets.UTF_8);
        return new Gson().fromJson(jsonString, TokenDecoder.class);
    }

}
