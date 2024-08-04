package com.example.OnlineFoodOrdering.repository;

import com.example.OnlineFoodOrdering.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    public List<Order> findByCustomerId(Long userId);
    public List<Order> findByRestaurantId(Long restaurantId);
}
