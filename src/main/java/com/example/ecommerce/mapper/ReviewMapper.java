package com.example.ecommerce.mapper;

import com.example.ecommerce.domain.Review;
import com.example.ecommerce.dto.review.ReviewRequest;
import com.example.ecommerce.dto.review.ReviewResponse;
import com.example.ecommerce.exception.InputFieldException;
import com.example.ecommerce.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ReviewMapper {

    private final CommonMapper commonMapper;
    private final ReviewService reviewService;

    public List<ReviewResponse> getReviewsByFlowerId(Long flowerId) {
        return commonMapper.convertToResponseList(reviewService.getReviewsByFlowerId(flowerId), ReviewResponse.class);
    }

    public ReviewResponse addReviewToFlower(ReviewRequest reviewRequest, Long flowerId, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InputFieldException(bindingResult);
        }
        Review review = commonMapper.convertToEntity(reviewRequest, Review.class);
        return commonMapper.convertToResponse(reviewService.addReviewToFlower(review, flowerId), ReviewResponse.class);
    }
}
