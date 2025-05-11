package com.example.OnlineFoodOrdering.controller;

import com.example.OnlineFoodOrdering.dto.response.*;
import com.example.OnlineFoodOrdering.exception.ResourceNotFoundException;
import com.example.OnlineFoodOrdering.model.*;
import com.example.OnlineFoodOrdering.dto.request.*;
import com.example.OnlineFoodOrdering.service.impl.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
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
@RequestMapping("api/v1/restaurant")
public class RestaurantController {
    RestaurantService restaurantService;
    UserService userService;

    @PostMapping
    public ResponseData<Long> saveRestaurant(
            @Valid @RequestBody RestaurantRequest res,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        try {
            UserEntity user = userService.findByUserByJwtToken(jwt);
            Restaurant restaurant = restaurantService.saveRestaurant(res, user);
            return new ResponseData<>(HttpStatus.CREATED.value(), "Restaurant created successfully", restaurant.getId());
        } catch (ResourceNotFoundException e) {
            log.error("Error = {} ", e.getMessage(), e.getCause());
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), "Create restaurant failed");
        }
    }

    @PutMapping("/{id}")
    public ResponseData<?> updateRestaurant(
            @Valid @RequestBody RestaurantRequest res,
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long id
    ) throws Exception {
        try {
            UserEntity user = userService.findByUserByJwtToken(jwt);
            Restaurant restaurant = restaurantService.updateRestaurant(id, res);
            return new ResponseData<>(HttpStatus.OK.value(), "Restaurant updated successfully", restaurant);
        } catch (ResourceNotFoundException e) {
            log.error("Error = {} ", e.getMessage(), e.getCause());
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), "Update restaurant failed");
        }
    }

    @PutMapping("/{id}/status")
    public ResponseData<?> updateRestaurantStatus(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long id
    ) throws Exception {
        try {
            UserEntity user = userService.findByUserByJwtToken(jwt);
            restaurantService.updateRestaurantStatus(id);
            return new ResponseData<>(HttpStatus.OK.value(), "Restaurant status updated successfully");
        } catch (ResourceNotFoundException e) {
            log.error("Error = {} ", e.getMessage(), e.getCause());
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), "Update restaurant status failed");
        }
    }

    @GetMapping("/user")
    public ResponseData<?> findRestaurantById(
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
       try {
           UserEntity user = userService.findByUserByJwtToken(jwt);
           if (user == null) {
               throw new Exception("User not found for the provided JWT");
           }
           Restaurant restaurant = restaurantService.getRestaurantByUserId(user.getId());
           return new ResponseData<>(HttpStatus.OK.value(), "Restaurant found", restaurant);
       } catch (ResourceNotFoundException e) {
           log.error("Error = {} ", e.getMessage(), e.getCause());
           return new ResponseError(HttpStatus.BAD_REQUEST.value(), "Restaurant not found");
       }
    }

    @GetMapping("/search")
    public ResponseData<?> searchRestaurant(
             @RequestHeader("Authorization") String jwt,
             @RequestParam("query") String keyWord
    ) throws Exception {
        try {
            UserEntity user = userService.findByUserByJwtToken(jwt);
            List<Restaurant> restaurant = restaurantService.searchRestaurant(keyWord);
            return new ResponseData<>(HttpStatus.OK.value(), "Restaurant found", restaurant);
        } catch (ResourceNotFoundException e) {
            log.error("Error = {} ", e.getMessage(), e.getCause());
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), "Restaurant not found");
        }
    }

    @GetMapping()
    public ResponseData<?> getAllRestaurant(
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        try {
            UserEntity user = userService.findByUserByJwtToken(jwt);
            List<Restaurant> restaurant = restaurantService.getAllRestaurants();
            return new ResponseData<>(HttpStatus.OK.value(), "Restaurant found", restaurant);
        } catch (ResourceNotFoundException e) {
            log.error("Error = {} ", e.getMessage(), e.getCause());
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), "Restaurant not found");
        }
    }

    @GetMapping("/{id}")
    public ResponseData<?> findRestaurantById(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long id
    ) throws Exception {
        try {
            UserEntity user = userService.findByUserByJwtToken(jwt);
            Restaurant restaurant = restaurantService.getRestaurantById(id);
            return new ResponseData<>(HttpStatus.OK.value(), "Restaurant found", restaurant);
        } catch (ResourceNotFoundException e) {
            log.error("Error = {} ", e.getMessage(), e.getCause());
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), "Restaurant not found");
        }
    }

    @PutMapping("/{id}/add-favorites")
    public ResponseData<?> addToFavorites(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long id
    ) throws Exception {
        try {
            UserEntity user = userService.findByUserByJwtToken(jwt);
            restaurantService.addToFavorites(id, user);
            return new ResponseData<>(HttpStatus.OK.value(), "Restaurant added to favorites");
        } catch (ResourceNotFoundException e) {
            log.error("Error = {} ", e.getMessage(), e.getCause());
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), "Add to favorites failed");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseData<?> deleteRestaurant(
            @RequestHeader("Authorization") String jwt,
            @PathVariable("userId") @Min(value = 1, message = "userId must be greater than 0") Long id
    ) throws Exception {
        try {
            UserEntity user = userService.findByUserByJwtToken(jwt);
            restaurantService.deleteRestaurant(id);
            return new ResponseData<>(HttpStatus.NO_CONTENT.value(), "Restaurant deleted successfully");
        } catch (ResourceNotFoundException e) {
            log.error("Error = {} ", e.getMessage(), e.getCause());
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), "Delete user failed");
        }

    }
}
