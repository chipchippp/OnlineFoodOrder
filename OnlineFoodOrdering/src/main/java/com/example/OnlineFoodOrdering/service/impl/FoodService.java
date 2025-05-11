package com.example.OnlineFoodOrdering.service.impl;

import com.example.OnlineFoodOrdering.model.Category;
import com.example.OnlineFoodOrdering.model.Food;
import com.example.OnlineFoodOrdering.model.Restaurant;
import com.example.OnlineFoodOrdering.dto.request.FoodRequest;

import java.util.List;

public interface FoodService {
    public Food saveFood(FoodRequest foodRequest, Restaurant restaurant) throws Exception;
    public void deleteFood(Long id) throws Exception;
//    public Food updateFood(Long id, FoodRequest foodRequest) throws Exception;
    public List<Food> getRestaurantFood(Long resId, boolean isVeg, boolean isSeasonal, boolean isNonVeg, String foodCategory) throws Exception;
    public List<Food> searchFood(String keyWord) throws Exception;
    public Food findFoodById(Long id) throws Exception;
    public Food updateAvailabilityStatus(Long id) throws Exception;

}
