package com.example.OnlineFoodOrdering.repository;

import com.example.OnlineFoodOrdering.model.IngredientCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IngredientCategoryRepository extends JpaRepository<IngredientCategory, Long> {
    public List<IngredientCategory> findByResId(Long id);
    public IngredientCategory findCategoryById(Long id);
}
