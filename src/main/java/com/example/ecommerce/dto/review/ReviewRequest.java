package com.example.ecommerce.dto.review;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import static com.example.ecommerce.constants.ErrorMessage.FILL_IN_THE_INPUT_FIELD;

@Data
public class ReviewRequest {

    private Long flowerId;

    @NotBlank(message = FILL_IN_THE_INPUT_FIELD)
    private String author;

    @NotBlank(message = FILL_IN_THE_INPUT_FIELD)
    private String message;

    @NotNull(message = "Choose flower rating")
    private Integer rating;
}
