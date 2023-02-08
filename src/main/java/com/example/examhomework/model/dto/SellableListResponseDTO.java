package com.example.examhomework.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SellableListResponseDTO {
    private Long id;
    private String name;
    @JsonProperty(value = "image_url")
    private String imageUrl;
}
