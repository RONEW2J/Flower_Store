package com.example.ecommerce.controller;

import com.example.ecommerce.dto.RegistrationRequest;
import com.example.ecommerce.mapper.AuthenticationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import static com.example.ecommerce.constants.PathConstants.ACTIVATE_CODE;
import static com.example.ecommerce.constants.PathConstants.API_V1_REGISTRATION;

@RestController
@RequiredArgsConstructor
@RequestMapping(API_V1_REGISTRATION)
public class RegistrationController {

    private final AuthenticationMapper authenticationMapper;

    @PostMapping
    public ResponseEntity<String> registration(@Valid @RequestBody RegistrationRequest user,
            BindingResult bindingResult) {
        return ResponseEntity.ok(authenticationMapper.registerUser(user.getCaptcha(), user, bindingResult));
    }

    @GetMapping(ACTIVATE_CODE)
    public ResponseEntity<String> activateEmailCode(@PathVariable String code) {
        return ResponseEntity.ok(authenticationMapper.activateUser(code));
    }
}
