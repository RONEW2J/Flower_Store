package com.example.ecommerce.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.ecommerce.dto.GraphQLRequest;
import com.example.ecommerce.dto.HeaderResponse;
import com.example.ecommerce.dto.flower.FlowerRequest;
import com.example.ecommerce.dto.flower.FlowerResponse;
import com.example.ecommerce.dto.order.OrderResponse;
import com.example.ecommerce.dto.user.BaseUserResponse;
import com.example.ecommerce.dto.user.UserResponse;
import com.example.ecommerce.mapper.FlowerMapper;
import com.example.ecommerce.mapper.OrderMapper;
import com.example.ecommerce.mapper.UserMapper;
import com.example.ecommerce.service.graphql.GraphQLProvider;

import graphql.ExecutionResult;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.example.ecommerce.constants.PathConstants.*;

@RestController
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ADMIN')")
@RequestMapping(API_V1_ADMIN)
public class AdminController {
    private final UserMapper userMapper;
    private final FlowerMapper flowerMapper;
    private final OrderMapper orderMapper;
    private final GraphQLProvider graphQLProvider;

    @PostMapping(ADD)
    public ResponseEntity<FlowerResponse> addFlower(
            @RequestPart(name = "file", required = false) MultipartFile file,
            @RequestPart("flower") @Valid FlowerRequest flower,
            BindingResult bindingResult) {
        return ResponseEntity.ok(flowerMapper.saveFlower(flower, file, bindingResult));
    }

    @PostMapping(EDIT)
    public ResponseEntity<FlowerResponse> updateFlower(
            @RequestPart(name = "file", required = false) MultipartFile file,
            @RequestPart("flower") @Valid FlowerRequest flower,
            BindingResult bindingResult) {
        return ResponseEntity.ok(flowerMapper.saveFlower(flower, file, bindingResult));
    }

    @DeleteMapping(DELETE_BY_FLOWER_ID)
    public ResponseEntity<String> deleteFlower(@PathVariable Long flowerId) {
        return ResponseEntity.ok(flowerMapper.deleteFlower(flowerId));
    }

    @GetMapping(ORDERS)
    public ResponseEntity<List<OrderResponse>> getAllOrders(@PageableDefault(size = 10) Pageable pageable) {
        HeaderResponse<OrderResponse> response = orderMapper.getAllOrders(pageable);
        return ResponseEntity.ok().headers(response.getHeaders()).body(response.getItems());
    }

    @GetMapping(ORDER_BY_EMAIL)
    public ResponseEntity<List<OrderResponse>> getUserOrdersByEmail(@PathVariable String userEmail,
            @PageableDefault(size = 10) Pageable pageable) {
        HeaderResponse<OrderResponse> response = orderMapper.getUserOrders(userEmail, pageable);
        return ResponseEntity.ok().headers(response.getHeaders()).body(response.getItems());
    }

    @DeleteMapping(ORDER_DELETE)
    public ResponseEntity<String> deleteOrder(@PathVariable Long orderId) {
        return ResponseEntity.ok(orderMapper.deleteOrder(orderId));
    }

    @GetMapping(USER_BY_ID)
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long userId) {
        return ResponseEntity.ok(userMapper.getUserById(userId));
    }

    @GetMapping(USER_ALL)
    public ResponseEntity<List<BaseUserResponse>> getAllUsers(@PageableDefault(size = 10) Pageable pageable) {
        HeaderResponse<BaseUserResponse> response = userMapper.getAllUsers(pageable);
        return ResponseEntity.ok().headers(response.getHeaders()).body(response.getItems());
    }

    @PostMapping(GRAPHQL_USER)
    public ResponseEntity<ExecutionResult> getUserByQuery(@RequestBody GraphQLRequest request) {
        return ResponseEntity.ok(graphQLProvider.getGraphQL().execute(request.getQuery()));
    }

    @PostMapping(GRAPHQL_USER_ALL)
    public ResponseEntity<ExecutionResult> getAllUsersByQuery(@RequestBody GraphQLRequest request) {
        return ResponseEntity.ok(graphQLProvider.getGraphQL().execute(request.getQuery()));
    }

    @PostMapping(GRAPHQL_ORDERS)
    public ResponseEntity<ExecutionResult> getAllOrdersQuery(@RequestBody GraphQLRequest request) {
        return ResponseEntity.ok(graphQLProvider.getGraphQL().execute(request.getQuery()));
    }

    @PostMapping(GRAPHQL_ORDER)
    public ResponseEntity<ExecutionResult> getUserOrdersByEmailQuery(@RequestBody GraphQLRequest request) {
        return ResponseEntity.ok(graphQLProvider.getGraphQL().execute(request.getQuery()));
    }
}
