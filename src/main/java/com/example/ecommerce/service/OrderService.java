package com.example.ecommerce.service;

import com.example.ecommerce.domain.Order;
import com.example.ecommerce.domain.OrderItem;
import graphql.schema.DataFetcher;

import java.util.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {
    Order getOrderById(Long orderId);

    List<OrderItem> getOrderItemsByOrderId(Long orderId);

    Page<Order> getAllOrders(Pageable pageable);

    Page<Order> getUserOrders(String email, Pageable pageable);

    Order postOrder(Order validOrder, Map<Long, Long> flowerId);

    String deleteOrder(Long orderId);

    DataFetcher<List<Order>> getAllOrdersByQuery();

    DataFetcher<List<Order>> getUserOrdersByEmailQuery();
}
