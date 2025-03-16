package com.example.OnlineFoodOrdering.service;

import com.example.OnlineFoodOrdering.service.impl.JwtService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class JwtServiceImpl implements JwtService {
    @Override
    public String generateToken(UserDetails username) {
        return null;
    }
}
