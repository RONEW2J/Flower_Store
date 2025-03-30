package com.example.ecommerce.controller;

import com.example.ecommerce.dto.review.ReviewRequest;
import com.example.ecommerce.dto.review.ReviewResponse;
import com.example.ecommerce.mapper.ReviewMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.example.ecommerce.constants.PathConstants.API_V1_REVIEW;
import static com.example.ecommerce.constants.PathConstants.FLOWER_ID;

@RestController
@RequiredArgsConstructor
@RequestMapping(API_V1_REVIEW)
public class ReviewController {

    private final ReviewMapper reviewMapper;
    private final SimpMessagingTemplate messagingTemplate;

    @GetMapping(FLOWER_ID)
    public ResponseEntity<List<ReviewResponse>> getReviewsByFlowerId(@PathVariable Long flowerId) {
        return ResponseEntity.ok(reviewMapper.getReviewsByFlowerId(flowerId));
    }

    @PostMapping
    public ResponseEntity<ReviewResponse> addReviewToFlower(@Validated @RequestBody ReviewRequest reviewRequest,
            BindingResult bindingResult) {
        ReviewResponse review = reviewMapper.addReviewToFlower(reviewRequest, reviewRequest.getFlowerId(),
                bindingResult);
        messagingTemplate.convertAndSend("/topic/reviews/" + reviewRequest.getFlowerId(), review);
        return ResponseEntity.ok(review);
    }
}
