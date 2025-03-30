package com.example.ecommerce.dto.flower;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class FullFlowerResponse extends FlowerResponse {
    private String species;
    private String color;
    private String fragrance;
    private String bloomingSeason;
    private String description;
    private String type;
    private MultipartFile imageFile;
}