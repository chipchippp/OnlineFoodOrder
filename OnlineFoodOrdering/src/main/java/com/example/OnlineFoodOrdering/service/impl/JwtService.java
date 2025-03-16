package com.example.OnlineFoodOrdering.service.impl;

import com.example.OnlineFoodOrdering.util.TokenType;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    String generateToken(UserDetails username);
    String extractUsername(String token, TokenType tokenType);
    boolean isValid(String token, UserDetails userDetails, TokenType tokenType);
    String generateRefreshToken(UserDetails user);
    String generateResetToken(UserDetails user);
}
