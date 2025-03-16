package com.example.OnlineFoodOrdering.service;

import com.example.OnlineFoodOrdering.dto.request.SignInRequest;
import com.example.OnlineFoodOrdering.dto.response.TokenResponse;
import com.example.OnlineFoodOrdering.exception.InvalidDataException;
import com.example.OnlineFoodOrdering.service.impl.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.token.TokenService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
//    private final JwtService jwtService;
//    private final TokenService tokenService;

    public TokenResponse accessToken(SignInRequest request) {
        log.info("---------- authenticate ----------");

        var user = userService.getByUsername(request.getUsername());
        if (!user.isEnabled()) {
            throw new InvalidDataException("User not active");
        }

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()));


        return null;
    }
}
