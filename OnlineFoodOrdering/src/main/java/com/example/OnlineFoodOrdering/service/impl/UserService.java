package com.example.OnlineFoodOrdering.service.impl;

import com.example.OnlineFoodOrdering.dto.request.UserRequestDTO;
import com.example.OnlineFoodOrdering.model.UserEntity;

public interface UserService {
    public UserEntity findByUserByJwtToken(String jwt) throws Exception;
    public UserEntity findUserByEmail(String email) throws Exception;
    long addUser(UserRequestDTO user);
    void updateUser(long userId, UserRequestDTO user);
    void deleteUser(long userId);
}
