package com.example.ecommerce.mapper;

import com.example.ecommerce.domain.User;
import com.example.ecommerce.dto.HeaderResponse;
import com.example.ecommerce.dto.flower.FlowerResponse;
import com.example.ecommerce.dto.user.BaseUserResponse;
import com.example.ecommerce.dto.user.UpdateUserRequest;
import com.example.ecommerce.dto.user.UserResponse;
import com.example.ecommerce.exception.InputFieldException;
import com.example.ecommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final CommonMapper commonMapper;
    private final UserService userService;

    public UserResponse getUserById(Long userId) {
        return commonMapper.convertToResponse(userService.getUserById(userId), UserResponse.class);
    }

    public UserResponse getUserInfo(String email) {
        return commonMapper.convertToResponse(userService.getUserInfo(email), UserResponse.class);
    }

    public List<FlowerResponse> getCart(List<Long> flowersIds) {
        return commonMapper.convertToResponseList(userService.getCart(flowersIds), FlowerResponse.class);
    }

    public HeaderResponse<BaseUserResponse> getAllUsers(Pageable pageable) {
        Page<User> users = userService.getAllUsers(pageable);
        return commonMapper.getHeaderResponse(users.getContent(), users.getTotalPages(), users.getTotalElements(),
                BaseUserResponse.class);
    }

    public UserResponse updateUserInfo(String email, UpdateUserRequest userRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InputFieldException(bindingResult);
        }
        User user = commonMapper.convertToEntity(userRequest, User.class);
        return commonMapper.convertToResponse(userService.updateUserInfo(email, user), UserResponse.class);
    }
}