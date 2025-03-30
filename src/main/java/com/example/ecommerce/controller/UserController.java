package com.example.ecommerce.controller;

import com.example.ecommerce.dto.GraphQLRequest;
import com.example.ecommerce.dto.flower.FlowerResponse;
import com.example.ecommerce.dto.user.UpdateUserRequest;
import com.example.ecommerce.dto.user.UserResponse;
import com.example.ecommerce.mapper.UserMapper;
import com.example.ecommerce.security.UserPrincipal;
import com.example.ecommerce.service.graphql.GraphQLProvider;
import graphql.ExecutionResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

import static com.example.ecommerce.constants.PathConstants.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(API_V1_USERS)
public class UserController {

    private final UserMapper userMapper;
    private final GraphQLProvider graphQLProvider;

    @GetMapping
    public ResponseEntity<UserResponse> getUserInfo(@AuthenticationPrincipal UserPrincipal user) {
        return ResponseEntity.ok(userMapper.getUserInfo(user.getEmail()));
    }

    @PutMapping
    public ResponseEntity<UserResponse> updateUserInfo(@AuthenticationPrincipal UserPrincipal user,
            @Valid @RequestBody UpdateUserRequest request,
            BindingResult bindingResult) {
        return ResponseEntity.ok(userMapper.updateUserInfo(user.getEmail(), request, bindingResult));
    }

    @PostMapping(CART)
    public ResponseEntity<List<FlowerResponse>> getCart(@RequestBody List<Long> flowersIds) {
        return ResponseEntity.ok(userMapper.getCart(flowersIds));
    }

    @PostMapping(GRAPHQL)
    public ResponseEntity<ExecutionResult> getUserInfoByQuery(@RequestBody GraphQLRequest request) {
        return ResponseEntity.ok(graphQLProvider.getGraphQL().execute(request.getQuery()));
    }
}
