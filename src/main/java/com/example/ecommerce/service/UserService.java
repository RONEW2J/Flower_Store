package com.example.ecommerce.service;

import com.example.ecommerce.domain.Flower;
import com.example.ecommerce.domain.User;
import graphql.schema.DataFetcher;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    User getUserById(Long userId);

    User getUserInfo(String email);

    Page<User> getAllUsers(Pageable pageable);

    List<Flower> getCart(List<Long> flowerIds);

    User updateUserInfo(String email, User user);

    DataFetcher<List<User>> getAllUsersByQuery();

    DataFetcher<User> getUserByQuery();
}
