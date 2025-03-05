package com.example.OnlineFoodOrdering.dto.response;

import com.example.OnlineFoodOrdering.util.ERole;
import lombok.Data;

@Data
public class AuthResponse {
    private String jwt;
    private String message;
    private ERole role;
}
