package com.example.ecommerce.dto.flower;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FlowerResponse {
    private Long id;
    private String flowerName;
    private String species;
    private Integer price;
    private Double flowerRating;
    private String filename;
    private Integer reviewsCount;
    private String size;
}
