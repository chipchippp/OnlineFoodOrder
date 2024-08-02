package com.example.OnlineFoodOrdering.service.impl;

import com.example.OnlineFoodOrdering.dto.RestaurantDto;
import com.example.OnlineFoodOrdering.model.Restaurant;
import com.example.OnlineFoodOrdering.model.UserEntity;
import com.example.OnlineFoodOrdering.request.RestaurantRequest;

import java.util.List;

public interface RestaurantService {
    public Restaurant saveRestaurant(RestaurantRequest res, UserEntity userEntity) throws Exception;
    public Restaurant updateRestaurant(Long id, RestaurantRequest updateRes) throws Exception;
    public void deleteRestaurant(Long id) throws Exception;
    public List<Restaurant> getAllRestaurants() throws Exception;
    public List<Restaurant> searchRestaurant(String keyWord) throws Exception;
    public Restaurant getRestaurantByUserId(Long id) throws Exception;
    public Restaurant findRestaurantById(Long userId) throws Exception;
    public RestaurantDto addToFavorites(Long restaurantId, UserEntity userEntity) throws Exception;
    public Restaurant updateRestaurantStatus(Long id) throws Exception;
}
