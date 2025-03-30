package com.example.ecommerce.service.Impl;

import com.example.ecommerce.domain.Flower;
import com.example.ecommerce.domain.Review;
import com.example.ecommerce.exception.ApiRequestException;
import com.example.ecommerce.repository.FlowerRepository;
import com.example.ecommerce.repository.ReviewRepository;
import com.example.ecommerce.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.ecommerce.constants.ErrorMessage.FLOWER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final FlowerRepository flowerRepository;
    private final ReviewRepository reviewRepository;

    @Override
    public List<Review> getReviewsByFlowerId(Long flowerId) {
        Flower flower = flowerRepository.findById(flowerId)
                .orElseThrow(() -> new ApiRequestException(FLOWER_NOT_FOUND, HttpStatus.NOT_FOUND));
        return flower.getReviews();
    }

    @Override
    @Transactional
    public Review addReviewToFlower(Review review, Long flowerId) {
        Flower flower = flowerRepository.findById(flowerId)
                .orElseThrow(() -> new ApiRequestException(FLOWER_NOT_FOUND, HttpStatus.NOT_FOUND));
        List<Review> reviews = flower.getReviews();
        reviews.add(review);
        double totalReviews = reviews.size();
        double sumRating = reviews.stream().mapToInt(Review::getRating).sum();
        flower.setFlowerRating(sumRating / totalReviews);
        return reviewRepository.save(review);
    }
}
