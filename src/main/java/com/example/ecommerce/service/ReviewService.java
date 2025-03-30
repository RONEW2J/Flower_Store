package com.example.ecommerce.service;

import com.example.ecommerce.domain.Review;

import java.util.List;

public interface ReviewService {
    List<Review> getReviewsByFlowerId(Long flowerId);

    Review addReviewToFlower(Review review, Long flowerId);
}
