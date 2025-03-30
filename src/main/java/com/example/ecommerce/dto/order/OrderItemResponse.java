package com.example.ecommerce.dto.order;

import com.example.ecommerce.dto.flower.FlowerResponse;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemResponse {
    private Long id;
    private Long amount;
    private Long quantity;
    private FlowerResponse flower;
}
