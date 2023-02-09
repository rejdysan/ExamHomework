package com.example.examhomework.controller;

import com.example.examhomework.model.Sellable;
import com.example.examhomework.model.User;
import com.example.examhomework.model.dto.LoginRequestDTO;
import com.example.examhomework.model.dto.SellableRequestDTO;
import com.example.examhomework.repository.SellableRepository;
import com.example.examhomework.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext
public class BidControllerTest {

    @Autowired
    MockMvc mvc;
    @Autowired
    UserRepository userRepository;
    @Autowired
    SellableRepository sellableRepository;
    @Autowired
    BCryptPasswordEncoder passwordEncoder;
    ObjectMapper objectMapper = new ObjectMapper();

    @BeforeAll
    public void setup() {
        User newUser = new User("test user", passwordEncoder.encode("Password1!"));
        userRepository.save(newUser);
        SellableRequestDTO sellable = new SellableRequestDTO(
            "nice smelling sock",
            "smells like heaven",
            "https://media.istockphoto.com/id/1324849113/photo/white-cotton-socks-on-white-background.jpg?s=612x612&w=0&k=20&c=MkoOYXjQO_en1EtROpj6lPD6SmYvm-dGhwBlTVAaijo=",
            new BigDecimal(3000),
            new BigDecimal(5000)
        );
        Sellable newSellable = sellableRepository.save(new Sellable(sellable, newUser));
    }

    @Test
    @Order(1)
    public void bidCreatedSuccessfully200() throws Exception {
        LoginRequestDTO request = new LoginRequestDTO("rejdysan", "Password1!");
        MvcResult result = mvc.perform(post("/api/login")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andReturn();
        String token = JsonPath.read(result.getResponse().getContentAsString(), "$.token");

        mvc.perform(post("/api/sellable/1/bid")
                .header("authorization", "Bearer " + token)
                .param("value", "3100"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.title").value("nice smelling sock"))
            .andExpect(jsonPath("$.description").value("smells like heaven"))
            .andExpect(jsonPath("$.bids").exists())
            .andExpect(jsonPath("$.seller").value("test user"))
            .andExpect(jsonPath("$.buyer").value("NOT SOLD YET - auction still ongoing"))
            .andExpect(jsonPath("$.image_url").value("https://media.istockphoto.com/id/1324849113/photo/white-cotton-socks-on-white-background.jpg?s=612x612&w=0&k=20&c=MkoOYXjQO_en1EtROpj6lPD6SmYvm-dGhwBlTVAaijo="))
            .andExpect(jsonPath("$.purchase_price").value(5000));
    }

    @Test
    @Order(2)
    public void bidNotCreatedLowBid400() throws Exception {
        LoginRequestDTO request = new LoginRequestDTO("rejdysan", "Password1!");
        MvcResult result = mvc.perform(post("/api/login")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andReturn();
        String token = JsonPath.read(result.getResponse().getContentAsString(), "$.token");

        mvc.perform(post("/api/sellable/1/bid")
                .header("authorization", "Bearer " + token)
                .param("value", "2000"))
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.message").value("Bid is equal or lower as starting price"));
    }
}
