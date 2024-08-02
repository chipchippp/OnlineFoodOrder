package com.example.OnlineFoodOrdering.service;

import com.example.OnlineFoodOrdering.config.JwtProvider;
import com.example.OnlineFoodOrdering.model.UserEntity;
import com.example.OnlineFoodOrdering.repository.UserRepository;
import com.example.OnlineFoodOrdering.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, JwtProvider jwtProvider) {
        this.userRepository = userRepository;
        this.jwtProvider = jwtProvider;
    }

    @Override
    public UserEntity findByUserByJwtToken(String jwt) throws Exception {
        String email = jwtProvider.getEmailFromJwtToken(jwt);
        UserEntity userEntity = userRepository.findUserByEmail(email);
        if (userEntity == null) {
            throw new Exception("User not found with email: " + email);
        }

        return userEntity;
    }

    @Override
    public UserEntity findUserByEmail(String email) throws Exception {
        UserEntity userEntity = userRepository.findUserByEmail(email);

        if (userEntity == null) {
            throw new Exception("User not found with email: " + email);
        }
        return userEntity;
    }
}
