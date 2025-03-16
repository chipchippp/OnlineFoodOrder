package com.example.OnlineFoodOrdering.service.impl;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    String generateToken(UserDetails username);
    //    String getUsernameFromToken(String token);
    //    boolean validateToken(String token);
}
