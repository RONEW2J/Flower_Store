package com.example.ecommerce.repository.projection;

import org.springframework.beans.factory.annotation.Value;

public interface FlowerProjection {
    Long getId();

    String getFlowerName();

    String getSpecies();

    String getColor();

    Integer getPrice();

    String getFilename();

    Double getFlowerRating();

    @Value("#{target.reviews.size()}")
    Integer getReviewsCount();

    void setFlowerName(String flowerName);

    void setSpecies(String species);

    void setPrice(Integer price);
}
