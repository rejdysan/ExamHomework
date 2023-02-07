package com.example.examhomework.model.dto;

import com.example.examhomework.model.Sellable;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SellableRequestDTO {
    @NotBlank(message = "Field name was empty!")
    @NotNull(message = "Field name was null!")
    private String name;
    @NotBlank(message = "Field description was empty!")
    @NotNull(message = "Field description was null!")
    private String description;
    @NotBlank(message = "Field image_url was empty!")
    @NotNull(message = "Field image_url was null!")
    @JsonProperty(value = "image_url")
    private String imageUrl;
//    @NotBlank(message = "Field starting_price was empty!")
    @NotNull(message = "Field starting_price was null!")
    @Min(value = 1L, message = "starting_price must be larger than 1")
    @Digits(integer = 1000000, fraction = 0, message = "starting_price cannot be decimal number")
    @JsonProperty(value = "starting_price")
    private BigDecimal startingPrice;
//    @NotBlank(message = "Field purchase_price was empty!")
    @NotNull(message = "Field purchase_price was null!")
    @Min(value = 1L, message = "purchase_price must be larger than 1")
    @Digits(integer = 1000000, fraction = 0, message = "purchase_price cannot be decimal number")
    @JsonProperty(value = "purchase_price")
    private BigDecimal purchasePrice;
}