package com.example.OnlineFoodOrdering.repository;

import com.example.OnlineFoodOrdering.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Integer>  {
    Optional<Token> findByUsername(String username);

}
