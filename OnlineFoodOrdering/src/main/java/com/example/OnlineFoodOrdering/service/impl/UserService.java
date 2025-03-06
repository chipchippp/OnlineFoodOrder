package com.example.OnlineFoodOrdering.service.impl;

import com.example.OnlineFoodOrdering.dto.request.UserRequestDTO;
import com.example.OnlineFoodOrdering.dto.response.UserDetailResponse;
import com.example.OnlineFoodOrdering.model.UserEntity;

public interface UserService {
    UserDetailResponse getUserId(long userId);
    UserEntity findByUserByJwtToken(String jwt) throws Exception;
    UserEntity findUserByEmail(String email) throws Exception;
    long addUser(UserRequestDTO request);
    void updateUser(long userId, UserRequestDTO request);
    void deleteUser(long userId);
}
