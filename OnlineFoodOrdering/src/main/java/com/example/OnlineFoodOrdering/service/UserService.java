package com.example.OnlineFoodOrdering.service;

import com.example.OnlineFoodOrdering.model.UserEntity;

import java.util.Optional;

public interface UserService {
    public UserEntity findByUsernameByJwtToken(String jwt) throws Exception;
    public UserEntity findUserByEmail(String email) throws Exception;
}
