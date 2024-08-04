package com.example.OnlineFoodOrdering.repository;

import com.example.OnlineFoodOrdering.model.IngredientItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IngredientRepository extends JpaRepository<IngredientItem, Long> {
    public List<IngredientItem> findByRestaurantId(Long id);
}
