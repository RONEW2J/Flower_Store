package com.example.ecommerce.service.Impl;

import static com.example.ecommerce.constants.ErrorMessage.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import com.example.ecommerce.domain.User;
import com.example.ecommerce.dto.CaptchaResponse;
import com.example.ecommerce.enums.AuthProvider;
import com.example.ecommerce.enums.Role;
import com.example.ecommerce.exception.ApiRequestException;
import com.example.ecommerce.exception.EmailException;
import com.example.ecommerce.exception.PasswordConfirmationException;
import com.example.ecommerce.exception.PasswordException;
import com.example.ecommerce.repository.UserRepository;
import com.example.ecommerce.security.JwtProvider;
import com.example.ecommerce.security.oauth2.OAuth2UserInfo;
import com.example.ecommerce.service.AuthenticationService;
import com.example.ecommerce.service.email.MailSender;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final RestTemplate restTemplate;
    private final JwtProvider jwtProvider;
    private final MailSender mailSender;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Value("${hostname}")
    private String hostname;

    @Value("${recaptcha.secret}")
    private String secret;

    @Value("${recaptcha.url}")
    private String captchaUrl;

    @Override
    public Map<String, Object> login(String email, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new ApiRequestException(EMAIL_NOT_FOUND, HttpStatus.NOT_FOUND));
            String userRole = user.getRoles().iterator().next().name();
            String token = jwtProvider.createToken(email, userRole);
            Map<String, Object> response = new HashMap<>();
            response.put("user", user);
            response.put("token", token);
            return response;
        } catch (AuthenticationException e) {
            throw new ApiRequestException(INCORRECT_PASSWORD, HttpStatus.FORBIDDEN);
        }
    }

    @Override
    @Transactional
    public String registerUser(User user, String captcha, String password2) {
        String url = String.format(captchaUrl, secret, captcha);
        restTemplate.postForObject(url, Collections.emptyList(), CaptchaResponse.class);

        if (user.getPassword() != null && !user.getPassword().equals(password2)) {
            throw new PasswordException(PASSWORDS_DO_NOT_MATCH);
        }

        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new EmailException(EMAIL_IN_USE);
        }
        user.setActive(false);
        user.setRoles(Collections.singleton(Role.USER));
        user.setProvider(AuthProvider.LOCAL);
        user.setActivationCode(UUID.randomUUID().toString());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        sendEmail(user, "Activation code", "registration-template", "registrationUrl",
                "/activate/" + user.getActivationCode());
        return "User successfully registered.";
    }

    @Override
    @Transactional
    public User registerOauth2User(String provider, OAuth2UserInfo oAuth2UserInfo) {
        User user = new User();
        user.setEmail(oAuth2UserInfo.getEmail());
        user.setFirstName(oAuth2UserInfo.getFirstName());
        user.setLastName(oAuth2UserInfo.getLastName());
        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        user.setProvider(AuthProvider.valueOf(provider.toUpperCase()));
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public User updateOauth2User(User user, String provider, OAuth2UserInfo oAuth2UserInfo) {
        user.setFirstName(oAuth2UserInfo.getFirstName());
        user.setLastName(oAuth2UserInfo.getLastName());
        user.setProvider(AuthProvider.valueOf(provider.toUpperCase()));
        return userRepository.save(user);
    }

    @Override
    public String getEmailByPasswordResetCode(String code) {
        return userRepository.getEmailByPasswordResetCode(code)
                .orElseThrow(() -> new ApiRequestException(INVALID_PASSWORD_CODE, HttpStatus.BAD_REQUEST));
    }

    @Override
    @Transactional
    public String sendPasswordResetCode(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ApiRequestException(EMAIL_NOT_FOUND, HttpStatus.NOT_FOUND));
        user.setPasswordResetCode(UUID.randomUUID().toString());
        userRepository.save(user);

        sendEmail(user, "Password reset", "password-reset-template", "resetUrl",
                "/reset/" + user.getPasswordResetCode());
        return "Reset password code is send to your E-mail";
    }

    @Override
    @Transactional
    public String passwordReset(String email, String password, String password2) {
        if (StringUtils.isEmpty(password2)) {
            throw new PasswordConfirmationException(EMPTY_PASSWORD_CONFIRMATION);
        }
        if (password != null && !password.equals(password2)) {
            throw new PasswordException(PASSWORDS_DO_NOT_MATCH);
        }
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ApiRequestException(EMAIL_NOT_FOUND, HttpStatus.NOT_FOUND));
        user.setPassword(passwordEncoder.encode(password));
        user.setPasswordResetCode(null);
        userRepository.save(user);
        return "Password successfully changed!";
    }

    @Override
    @Transactional
    public String activateUser(String code) {
        User user = userRepository.findByActivationCode(code)
                .orElseThrow(() -> new ApiRequestException(ACTIVATION_CODE_NOT_FOUND, HttpStatus.NOT_FOUND));
        user.setActivationCode(null);
        user.setActive(true);
        userRepository.save(user);
        return "User successfully activated.";
    }

    private void sendEmail(User user, String subject, String template, String urlAttribute, String urlPath) {
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("firstName", user.getFirstName());
        attributes.put(urlAttribute, "http://" + hostname + urlPath);
        mailSender.sendMessageHtml(user.getEmail(), subject, template, attributes);
    }
}