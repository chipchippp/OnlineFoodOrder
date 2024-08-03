package com.example.OnlineFoodOrdering.controller;

import com.example.OnlineFoodOrdering.model.Category;
import com.example.OnlineFoodOrdering.model.Food;
import com.example.OnlineFoodOrdering.model.Restaurant;
import com.example.OnlineFoodOrdering.model.UserEntity;
import com.example.OnlineFoodOrdering.request.FoodRequest;
import com.example.OnlineFoodOrdering.response.MessageResponse;
import com.example.OnlineFoodOrdering.service.impl.FoodService;
import com.example.OnlineFoodOrdering.service.impl.RestaurantService;
import com.example.OnlineFoodOrdering.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/admin/food")
public class AdminFoodController {
    private final FoodService foodService;
    private final UserService userService;
    private final RestaurantService restaurantService;

    @Autowired
    public AdminFoodController(FoodService foodService, UserService userService, RestaurantService restaurantService) {
        this.foodService = foodService;
        this.userService = userService;
        this.restaurantService = restaurantService;
    }

    @PostMapping
    public ResponseEntity<Food> saveFood(
            @RequestBody FoodRequest foodRequest,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {

        UserEntity user = userService.findByUserByJwtToken(jwt);
        Restaurant restaurant = restaurantService.findRestaurantById(foodRequest.getRestaurantId());
        Food food = foodService.saveFood(foodRequest, foodRequest.getCategory(), restaurant);
        return new ResponseEntity<>(food, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Food> updateFood(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long id
    ) throws Exception {

        UserEntity user = userService.findByUserByJwtToken(jwt);
        Food food = foodService.updateAvailabilityStatus(id);
        return new ResponseEntity<>(food, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteFood(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long id
    ) throws Exception {

        UserEntity user = userService.findByUserByJwtToken(jwt);
        foodService.deleteFood(id);

        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setMessage("Food deleted successfully");
        return new ResponseEntity<>(messageResponse, HttpStatus.OK);
    }

}
