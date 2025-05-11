package com.example.OnlineFoodOrdering.dto.response;

import com.example.OnlineFoodOrdering.util.ERole;
import lombok.*;

@Getter
@Setter
@Builder
public class AuthResponse {
    private String accessToken;
    private String refreshToken;
    private ERole role;
}
