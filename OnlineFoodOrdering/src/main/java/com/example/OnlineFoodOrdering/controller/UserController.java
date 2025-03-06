package com.example.OnlineFoodOrdering.controller;

import com.example.OnlineFoodOrdering.dto.request.UserRequestDTO;
import com.example.OnlineFoodOrdering.dto.response.ResponseData;
import com.example.OnlineFoodOrdering.dto.response.ResponseError;
import com.example.OnlineFoodOrdering.dto.response.UserDetailResponse;
import com.example.OnlineFoodOrdering.exception.ResourceNotFoundException;
import com.example.OnlineFoodOrdering.model.UserEntity;
import com.example.OnlineFoodOrdering.service.impl.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<UserEntity> findUserByJwtToken(
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        UserEntity user = userService.findByUserByJwtToken(jwt);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/email")
    public ResponseEntity<UserEntity> getUserByEmail(String email) {
        try {
            UserEntity user = userService.findUserByEmail(email);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

//    @Operation(summary = "Get user by id", description = "Get user by id")
    @GetMapping("/{userId}")
    public ResponseData<UserDetailResponse> getUserId(@PathVariable @Min(1) long userId) {
        log.info("User id: " + userId);
        try {
            UserDetailResponse user = userService.getUserId(userId);
            return new ResponseData<>(HttpStatus.OK.value(), "User found", user);
        }catch (ResourceNotFoundException e){
            log.error("Error = {} ", e.getMessage(), e.getCause());
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }
    }

    @PostMapping("/add")
    public ResponseData<Long> addUser(@Valid @RequestBody UserRequestDTO request) {
        log.info("Request add user = {} {}: ", request.getFullName());
        try {
            long userId = userService.addUser(request);
            return new ResponseData<>(HttpStatus.CREATED.value(), "user.add.success", userId);
        } catch (Exception e) {
            log.error("Error = {} ", e.getMessage(), e.getCause());
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), "Add user fail");
        }
    }

    @PutMapping("/{userId}")
    public ResponseData<Long> updateUser(@PathVariable @Min(1) long userId, @Valid @RequestBody UserRequestDTO request) {
        log.info("Request update user = {} {}: ", userId);
        try {
            userService.updateUser(userId, request);
            return new ResponseData<>(HttpStatus.ACCEPTED.value(), "user.add.success", userId);
        } catch (Exception e) {
            log.error("Error = {} ", e.getMessage(), e.getCause());
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), "Update user failed");
        }
    }

    @DeleteMapping("/{userId}")
    public ResponseData<Long> deleteUser(@PathVariable @Min(value = 1, message = "userId must be greater than 0") long userId) {
        log.info("Request update user = {} {}: ", userId);
        try {
            userService.deleteUser(userId);
            return new ResponseData<>(HttpStatus.NO_CONTENT.value(), "user.add.success", userId);
        } catch (Exception e) {
            log.error("Error = {} ", e.getMessage(), e.getCause());
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), "Delete user failed");
        }
    }
}
