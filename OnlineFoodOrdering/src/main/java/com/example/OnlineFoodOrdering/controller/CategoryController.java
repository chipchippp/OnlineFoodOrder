package com.example.OnlineFoodOrdering.controller;

import com.example.OnlineFoodOrdering.dto.response.*;
import com.example.OnlineFoodOrdering.model.*;
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
@RequestMapping("/api/v1/category")
public class CategoryController {
    CategoryService categoryService;
    UserService userService;
    RestaurantService restaurantService;

    @GetMapping
    public ResponseData<?> getAllCategories(
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        try {
            UserEntity user = userService.findByUserByJwtToken(jwt);
            List<Category> categories = categoryService.findAllCategories();
            return new ResponseData<>(HttpStatus.OK.value(), "Get all categories successfully", categories);
        } catch (Exception e) {
            log.error("Error = {} ", e.getMessage(), e.getCause());
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), "Get all categories failed");
        }
    }

    @GetMapping("/{id}")
    public ResponseData<?> getCategoryById(
            @PathVariable Long id,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        try {
            UserEntity user = userService.findByUserByJwtToken(jwt);
            Category category = categoryService.findCategoryById(id);
            return new ResponseData<>(HttpStatus.OK.value(), "Get category successfully", category);
        } catch (Exception e) {
            log.error("Error = {} ", e.getMessage(), e.getCause());
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), "Get category failed");
        }
    }

    @PostMapping("/add")
    public ResponseData<?> saveCategory(
            @Valid @RequestBody Category category,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        try {
            UserEntity user = userService.findByUserByJwtToken(jwt);
            Category createCategory = categoryService.saveCategory(category.getName(), user.getId());
            return new ResponseData<>(HttpStatus.CREATED.value(), "Create category successfully", createCategory);
        } catch (Exception e) {
            log.error("Error = {} ", e.getMessage(), e.getCause());
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), "Create category failed");
        }
    }

    @PostMapping("/restaurants")
    public ResponseData<?> getResCategory(
            @Valid @RequestBody Category category,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        try {
            UserEntity user = userService.findByUserByJwtToken(jwt);
            List<Category> categories = categoryService.findCategoryByResId(category.getId());
            return new ResponseData<>(HttpStatus.OK.value(), "Get restaurant category successfully", categories);
        } catch (Exception e) {
            log.error("Error = {} ", e.getMessage(), e.getCause());
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), "Get restaurant category failed");
        }
    }
}
