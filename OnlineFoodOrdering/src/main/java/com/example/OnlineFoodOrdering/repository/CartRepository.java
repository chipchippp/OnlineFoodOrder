package com.example.OnlineFoodOrdering.repository;

import com.example.OnlineFoodOrdering.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
