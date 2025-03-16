package com.example.OnlineFoodOrdering.repository;

import com.example.OnlineFoodOrdering.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findUserByUsername(String username);
    Optional<UserEntity> findUserByEmail(String email);
//    @Query(value = "select r.name from Role r inner join UserHasRole ur on r.id = ur.user.id where ur.id= :userId")
//    List<String> findAllRolesByUserId(Long userId);
}
