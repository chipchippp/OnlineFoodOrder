package com.example.OnlineFoodOrdering.controller;

import com.example.OnlineFoodOrdering.dto.response.*;
import com.example.OnlineFoodOrdering.model.*;
import com.example.OnlineFoodOrdering.dto.request.*;
import com.example.OnlineFoodOrdering.service.impl.*;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/food")
public class FoodController {
    FoodService foodService;
    UserService userService;
    RestaurantService restaurantService;

    @PostMapping
    public ResponseData<?> saveFood(
            @Valid @RequestBody FoodRequest foodRequest,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        try {
            UserEntity user = userService.findByUserByJwtToken(jwt);
            Restaurant restaurant = restaurantService.getRestaurantById(foodRequest.getRestaurantId());
            Food food = foodService.saveFood(foodRequest, restaurant);
            return new ResponseData<>(HttpStatus.CREATED.value(), "Food created successfully", food);
        } catch (Exception e) {
            log.error("Error = {} ", e.getMessage(), e.getCause());
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), "Create food failed");
        }
    }

    @PutMapping("/{id}")
    public ResponseData<?> updateFood(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long id
    ) throws Exception {
        try {
            UserEntity user = userService.findByUserByJwtToken(jwt);
            Food food = foodService.updateAvailabilityStatus(id);
            return new ResponseData<>(HttpStatus.OK.value(), "Food updated successfully", food);
        } catch (Exception e) {
            log.error("Error = {} ", e.getMessage(), e.getCause());
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), "Update food failed");
        }
    }

    @GetMapping("/search")
    public ResponseData<?> searchFood(
            @RequestParam("query") String name,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        try {
            UserEntity user = userService.findByUserByJwtToken(jwt);
            List<Food> foodList = foodService.searchFood(name);
            return new ResponseData<>(HttpStatus.OK.value(), "Search food successfully", foodList);
        } catch (Exception e) {
            log.error("Error = {} ", e.getMessage(), e.getCause());
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), "Search food failed");
        }
    }

    @GetMapping("/restaurant/{restaurantId}")
    public ResponseData<?> getResFood(
            @PathVariable Long restaurantId,
            @RequestParam boolean isVeg,
            @RequestParam boolean seasonal,
            @RequestParam boolean nonVeg,
            @RequestParam(required = false) String foodCategory,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        try {
            UserEntity user = userService.findByUserByJwtToken(jwt);
            List<Food> foodList = foodService.getRestaurantFood(restaurantId, isVeg, seasonal, nonVeg, foodCategory);
            return new ResponseData<>(HttpStatus.OK.value(), "Get restaurant food successfully", foodList);
        } catch (Exception e) {
            log.error("Error = {} ", e.getMessage(), e.getCause());
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), "Get restaurant food failed");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseData<?> deleteFood(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long id
    ) throws Exception {
        try {
            UserEntity user = userService.findByUserByJwtToken(jwt);
            foodService.deleteFood(id);
            return new ResponseData<>(HttpStatus.OK.value(), "Food deleted successfully");
        } catch (Exception e) {
            log.error("Error = {} ", e.getMessage(), e.getCause());
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), "Delete food failed");
        }
    }

}
