package com.example.OnlineFoodOrdering.controller;

import com.example.OnlineFoodOrdering.dto.response.*;
import com.example.OnlineFoodOrdering.model.*;
import com.example.OnlineFoodOrdering.dto.request.*;
import com.example.OnlineFoodOrdering.service.impl.*;
import com.example.OnlineFoodOrdering.exception.ResourceNotFoundException;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    UserService userService;

    @GetMapping("/profile")
    public ResponseData<?> findUserByJwtToken(
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        try {
            UserEntity user = userService.findByUserByJwtToken(jwt);
            return new ResponseData<>(HttpStatus.OK.value(), "user.get.success", user);
        } catch (ResourceNotFoundException e) {
            log.error("Error = {} ", e.getMessage(), e.getCause());
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), "Get user fail");
        }
    }

    @GetMapping("/email")
    public ResponseData<?> getUserByEmail(String email) {
        try {
            UserEntity user = userService.findUserByEmail(email);
            return new ResponseData<>(HttpStatus.OK.value(), "user.get.success", user);
        } catch (Exception e) {
            log.error("Error = {} ", e.getMessage(), e.getCause());
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), "Get user fail");
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
}
