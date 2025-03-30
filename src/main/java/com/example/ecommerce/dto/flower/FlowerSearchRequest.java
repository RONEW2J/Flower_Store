package com.example.ecommerce.dto.flower;

import lombok.Data;

import java.util.List;

@Data
public class FlowerSearchRequest {
    private List<String> flowerNames;
    private List<String> colors;
    private List<Integer> prices;
    private Boolean sortByPrice;
    private String species;
    private String bloomingSeason;
}