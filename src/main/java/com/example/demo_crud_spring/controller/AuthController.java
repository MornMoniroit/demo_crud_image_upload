package com.example.demo_crud_spring.controller;

import com.example.demo_crud_spring.model.response.ApiResponse;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @GetMapping("/me")
    public ApiResponse<Map<String, Object>> me(OAuth2User principal) {
        return new ApiResponse<>("Authenticated user", principal.getAttributes(), LocalDateTime.now());
    }
}
