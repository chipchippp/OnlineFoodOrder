package com.example.OnlineFoodOrdering.controller;

import com.example.OnlineFoodOrdering.model.Food;
import com.example.OnlineFoodOrdering.model.Restaurant;
import com.example.OnlineFoodOrdering.model.UserEntity;
import com.example.OnlineFoodOrdering.request.FoodRequest;
import com.example.OnlineFoodOrdering.service.impl.FoodService;
import com.example.OnlineFoodOrdering.service.impl.RestaurantService;
import com.example.OnlineFoodOrdering.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/food")
public class FoodController {
    private final FoodService foodService;
    private final UserService userService;
    private final RestaurantService restaurantService;

    @Autowired
    public FoodController(FoodService foodService, UserService userService, RestaurantService restaurantService) {
        this.foodService = foodService;
        this.userService = userService;
        this.restaurantService = restaurantService;
    }

    @GetMapping("/search")
    public ResponseEntity<List<Food>> searchFood(
            @RequestParam("query") String name,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {

        UserEntity user = userService.findByUserByJwtToken(jwt);
        List<Food> food = foodService.searchFood(name);
        return new ResponseEntity<>(food, HttpStatus.CREATED);
    }

    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<Food>> getResFood(
            @PathVariable Long restaurantId,
            @RequestParam boolean isVeg,
            @RequestParam boolean seasonal,
            @RequestParam boolean nonVeg,
            @RequestParam(required = false) String foodCategory,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {

        UserEntity user = userService.findByUserByJwtToken(jwt);
        List<Food> food = foodService.getRestaurantFood(restaurantId, isVeg, seasonal, nonVeg, foodCategory);
        return new ResponseEntity<>(food, HttpStatus.OK);
    }

}
