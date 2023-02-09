package com.example.examhomework.controller;

import com.example.examhomework.model.dto.LoginRequestDTO;
import com.example.examhomework.model.dto.RegisterRequestDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext
public class UserControllerTest {

    @Autowired
    MockMvc mvc;
    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @Order(1)
    public void userSuccessfullyRegistered200() throws Exception {
        RegisterRequestDTO request = new RegisterRequestDTO("test user", "Password1!");
        mvc.perform(post("/api/register")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(3))
            .andExpect(jsonPath("$.username").value("test user"));
    }

    @Test
    @Order(2)
    public void registrationInvalidPassword400() throws Exception {
        RegisterRequestDTO request = new RegisterRequestDTO("test user 2", "Password");
        mvc.perform(post("/api/register")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.message").value("Password must contain at least one uppercase, one lowercase, one digit, one special character."));
    }

    @Test
    @Order(3)
    public void userSuccessfullyLoggedIn200() throws Exception {
        LoginRequestDTO request = new LoginRequestDTO("test user", "Password1!");
        mvc.perform(post("/api/login")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.token").exists())
            .andExpect(jsonPath("$.green_dollars").value(50000));
    }

    @Test
    @Order(4)
    public void loginInvalidUsername400() throws Exception {
        LoginRequestDTO request = new LoginRequestDTO("test us", "Password1!");
        mvc.perform(post("/api/login")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.message").value("Username or password incorrect"));
    }

    @Test
    @Order(5)
    public void userSuccessfullyToppedUp200() throws Exception {
        LoginRequestDTO request = new LoginRequestDTO("test user", "Password1!");
        MvcResult result = mvc.perform(post("/api/login")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andReturn();
        String token = JsonPath.read(result.getResponse().getContentAsString(), "$.token");

        mvc.perform(put("/api/topup")
                .header("authorization", "Bearer " + token)
                .param("amount", "1000"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(3))
            .andExpect(jsonPath("$.username").value("test user"))
            .andExpect(jsonPath("$.green_dollars").value(51000));
    }

    @Test
    @Order(6)
    public void topupFailedInvalidToken401() throws Exception {
        String token = "wrong.token.forsure";

        mvc.perform(put("/api/topup")
                .header("authorization", "Bearer " + token)
                .param("amount", "1000"))
            .andExpect(status().isUnauthorized())
            .andExpect(header().stringValues("WWW-Authenticate", "Bearer error=\"invalid_token\", error_description=\"An error occurred while attempting to decode the Jwt: Invalid unsecured/JWS/JWE header: Invalid JSON: java.lang.IllegalStateException: Expected BEGIN_OBJECT but was STRING at line 1 column 1 path $\", error_uri=\"https://tools.ietf.org/html/rfc6750#section-3.1\""));
    }
}
