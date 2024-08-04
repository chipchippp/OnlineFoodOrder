package com.example.OnlineFoodOrdering.controller;

import com.example.OnlineFoodOrdering.model.Category;
import com.example.OnlineFoodOrdering.model.IngredientCategory;
import com.example.OnlineFoodOrdering.model.IngredientItem;
import com.example.OnlineFoodOrdering.model.UserEntity;
import com.example.OnlineFoodOrdering.request.IngredientCategoryRequest;
import com.example.OnlineFoodOrdering.request.IngredientRequest;
import com.example.OnlineFoodOrdering.service.impl.IngredientsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/ingredient")
public class IngredientController {
    private final IngredientsService ingredientService;

    @Autowired
    public IngredientController(IngredientsService ingredientService) {
        this.ingredientService = ingredientService;
    }


    @PostMapping("/category")
    public ResponseEntity<IngredientCategory> saveIngredientCategory(
            @RequestBody IngredientCategoryRequest req,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {

        IngredientCategory item = ingredientService.saveIngredient(req.getName(), req.getResId());
        return new ResponseEntity<>(item, HttpStatus.CREATED);
    }

    @PostMapping("/categoryItem")
    public ResponseEntity<IngredientItem> saveIngredientItem(
            @RequestBody IngredientRequest req
    ) throws Exception {

        IngredientItem item = ingredientService.saveIngredientItem(req.getName(), req.getResId(), req.getCategoryId());
        return new ResponseEntity<>(item, HttpStatus.CREATED);
    }

    @PutMapping("/{id}/stock")
    public ResponseEntity<IngredientItem> updateIngredientStock(
            @PathVariable Long id
    ) throws Exception {

        IngredientItem item = ingredientService.updateStock(id);
        return new ResponseEntity<>(item, HttpStatus.OK);
    }

    @GetMapping("/restaurant/{id}")
    public ResponseEntity<List<IngredientItem>> getRestaurantIngredient(
            @PathVariable Long id
    ) throws Exception {

        List<IngredientItem> item = ingredientService.findResIngredient(id);
        return new ResponseEntity<>(item, HttpStatus.OK);
    }

    @GetMapping("/restaurant/{id}/category")
    public ResponseEntity<List<IngredientCategory>> getRestaurantIngredientCategory(
            @PathVariable Long id
    ) throws Exception {

        List<IngredientCategory> item = ingredientService.findIngredientCategoryByResId(id);
        return new ResponseEntity<>(item, HttpStatus.OK);
    }
}
