package com.example.ecommerce.service;

import com.example.ecommerce.domain.User;
import com.example.ecommerce.security.oauth2.OAuth2UserInfo;

import java.util.*;

public interface AuthenticationService {
    Map<String, Object> login(String email, String password);

    String registerUser(User user, String captcha, String password2);

    User registerOauth2User(String provider, OAuth2UserInfo oAuth2UserInfo);

    User updateOauth2User(User user, String provider, OAuth2UserInfo oAuth2UserInfo);

    String activateUser(String code);

    String getEmailByPasswordResetCode(String code);

    String sendPasswordResetCode(String email);

    String passwordReset(String email, String password, String password2);
}
