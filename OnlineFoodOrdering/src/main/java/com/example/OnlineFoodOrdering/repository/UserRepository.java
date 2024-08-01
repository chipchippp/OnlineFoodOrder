package com.example.OnlineFoodOrdering.repository;

import com.example.OnlineFoodOrdering.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByEmail(String email);
    UserEntity findByUsername(String username);
    UserEntity findByUsernameAndPassword(String username, String password);
}
