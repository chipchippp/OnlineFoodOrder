package com.example.OnlineFoodOrdering.repository;

import com.example.OnlineFoodOrdering.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findUserByEmail(String email);
    UserEntity findByUsernameAndPassword(String username, String password);
}
