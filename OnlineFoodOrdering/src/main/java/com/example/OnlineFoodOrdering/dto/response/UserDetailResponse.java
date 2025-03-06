package com.example.OnlineFoodOrdering.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UserDetailResponse {
    private Long id;
    private String fullName;
    private String username;
    private String phone;
    private String email;
}
