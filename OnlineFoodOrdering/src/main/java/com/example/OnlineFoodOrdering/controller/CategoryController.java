package com.example.OnlineFoodOrdering.controller;

import com.example.OnlineFoodOrdering.model.Category;
import com.example.OnlineFoodOrdering.model.UserEntity;
import com.example.OnlineFoodOrdering.service.impl.CategoryService;
import com.example.OnlineFoodOrdering.service.impl.RestaurantService;
import com.example.OnlineFoodOrdering.service.impl.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {
    private final CategoryService categoryService;
    private final UserService userService;
    private final RestaurantService restaurantService;


    @PostMapping("/add")
    public ResponseEntity<Category> saveCategory(
            @RequestBody Category category,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {

        UserEntity user = userService.findByUserByJwtToken(jwt);
        Category createCategory = categoryService.saveCategory(category.getName(), user.getId());
        return new ResponseEntity<>(category, HttpStatus.CREATED);
    }

    @PostMapping("/restaurants")
    public ResponseEntity<List<Category>> getResCategory(
            @RequestBody Category category,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {

        UserEntity user = userService.findByUserByJwtToken(jwt);
        List<Category> categories = categoryService.findCategoryByResId(user.getId());
        return new ResponseEntity<>(categories, HttpStatus.CREATED);
    }
}
