package com.example.OnlineFoodOrdering.service.impl;

import com.example.OnlineFoodOrdering.dto.request.LoginRequest;
import com.example.OnlineFoodOrdering.dto.response.AuthResponse;
import com.example.OnlineFoodOrdering.model.UserEntity;

public interface AuthService {
    AuthResponse login(LoginRequest loginRequest);
    AuthResponse register(UserEntity user) throws Exception;
}
