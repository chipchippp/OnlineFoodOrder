package com.example.OnlineFoodOrdering.service.impl;

import com.example.OnlineFoodOrdering.model.UserEntity;

public interface UserService {
    public UserEntity findByUserByJwtToken(String jwt) throws Exception;
    public UserEntity findUserByEmail(String email) throws Exception;
}
