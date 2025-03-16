package com.example.OnlineFoodOrdering.dto.request;

import com.example.OnlineFoodOrdering.util.Platform;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class SignInRequest {
    @NotBlank(message = "Username is required")
    private String username;
    @NotBlank(message = "Password is required")
    private String password;
    @NotNull(message = "Platform is required")
    private Platform platform;

    private String deviceToken;
    private String version;
}
