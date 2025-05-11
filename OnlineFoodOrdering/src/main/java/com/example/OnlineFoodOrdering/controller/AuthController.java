package com.example.OnlineFoodOrdering.controller;

import com.example.OnlineFoodOrdering.dto.response.*;
import com.example.OnlineFoodOrdering.model.*;
import com.example.OnlineFoodOrdering.dto.request.*;
import com.example.OnlineFoodOrdering.service.impl.*;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
//@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthController {
    AuthService authService;

    @PostMapping("/signup")
    public ResponseData<?> createUserHandler(@Valid @RequestBody UserEntity user) throws Exception {
        try {
            AuthResponse authResponse = authService.register(user);
            return new ResponseData<>(HttpStatus.CREATED.value(), "User created successfully", authResponse);
        } catch (Exception e) {
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseData<?> loginUserHandler(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            AuthResponse authResponse = authService.login(loginRequest);
            return new ResponseData<>(HttpStatus.OK.value(), "Login successfully", authResponse);
        } catch (BadCredentialsException e) {
            return new ResponseError(HttpStatus.UNAUTHORIZED.value(), "Invalid credentials");
        } catch (Exception e) {
            return new ResponseError(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        }
    }
}
