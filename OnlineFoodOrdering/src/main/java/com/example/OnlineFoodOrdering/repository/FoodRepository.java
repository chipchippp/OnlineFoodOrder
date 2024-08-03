package com.example.OnlineFoodOrdering.repository;

import com.example.OnlineFoodOrdering.model.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Repository
public interface FoodRepository extends JpaRepository<Food, Long> {
    List<Food> findByRestaurantId(Long restaurantId);
    @Query("SELECT f FROM Food f WHERE f.name LIKE %:keyword% " +
            "OR f.category.name LIKE %:keyword%")
    List<Food> searchFood(@RequestParam("keyword") String keyword);

}
