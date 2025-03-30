package com.example.ecommerce.dto.flower;

import com.example.ecommerce.enums.SearchFlower;
import lombok.Data;

@Data
public class SearchTypeRequest {
    private SearchFlower searchType;
    private String text;
}