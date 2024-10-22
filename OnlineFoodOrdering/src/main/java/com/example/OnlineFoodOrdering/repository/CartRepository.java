package com.example.OnlineFoodOrdering.repository;

import com.example.OnlineFoodOrdering.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    public Cart findCartByCustomerId(Long userId);
}
