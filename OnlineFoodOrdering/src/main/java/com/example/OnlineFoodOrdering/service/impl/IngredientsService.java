package com.example.OnlineFoodOrdering.service.impl;

import com.example.OnlineFoodOrdering.model.IngredientCategory;
import com.example.OnlineFoodOrdering.model.IngredientItem;

import java.util.List;

public interface IngredientsService {
    public IngredientCategory saveIngredient(String name, Long resId) throws Exception;
//    public void deleteIngredient(Long id) throws Exception;
//    public void updateIngredient(Long id, String name) throws Exception;
    public IngredientCategory findIngredientCategoryById(Long id) throws Exception;
    public List<IngredientCategory> findIngredientCategoryByResId(Long id) throws Exception;
    public IngredientItem saveIngredientItem(String ingredientName, Long resId, Long categoryId) throws Exception;
    public List<IngredientItem> findResIngredient(Long resId) throws Exception;
    public IngredientItem updateStock(Long id) throws Exception;

}
