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
@RequestMapping("/api/v1/admin/ingredient")
public class IngredientController {
    IngredientsService ingredientService;

    @PostMapping("/category")
    public ResponseData<?> saveIngredientCategory(
            @Valid @RequestBody IngredientCategoryRequest req,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        try {
            IngredientCategory category = ingredientService.saveIngredient(req.getName(), req.getResId());
            return new ResponseData<>(HttpStatus.CREATED.value(), "Create ingredient category successfully", category);
        } catch (Exception e) {
            log.error("Error = {} ", e.getMessage(), e.getCause());
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), "Create ingredient category failed");
        }
    }

    @PostMapping("/categoryItem")
    public ResponseData<?> saveIngredientItem(
            @Valid @RequestBody IngredientRequest req
    ) throws Exception {
        try {
            IngredientItem item = ingredientService.saveIngredientItem(req.getName(), req.getResId(), req.getCategoryId());
            return new ResponseData<>(HttpStatus.CREATED.value(), "Create ingredient item successfully", item);
        } catch (Exception e) {
            log.error("Error = {} ", e.getMessage(), e.getCause());
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), "Create ingredient item failed");
        }
    }

    @PutMapping("/{id}/stock")
    public ResponseData<?> updateIngredientStock(
            @PathVariable Long id
    ) throws Exception {
        try {
            IngredientItem item = ingredientService.updateStock(id);
            return new ResponseData<>(HttpStatus.OK.value(), "Update ingredient stock successfully", item);
        } catch (Exception e) {
            log.error("Error = {} ", e.getMessage(), e.getCause());
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), "Update ingredient stock failed");
        }
    }

    @GetMapping("/restaurant/{id}")
    public ResponseData<?> getRestaurantIngredient(
            @PathVariable Long id
    ) throws Exception {
        try {
            List<IngredientItem> item = ingredientService.findResIngredient(id);
            return new ResponseData<>(HttpStatus.OK.value(), "Get restaurant ingredient successfully", item);
        } catch (Exception e) {
            log.error("Error = {} ", e.getMessage(), e.getCause());
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), "Get restaurant ingredient failed");
        }
    }

    @GetMapping("/restaurant/{id}/category")
    public ResponseData<?> getRestaurantIngredientCategory(
            @PathVariable Long id
    ) throws Exception {
        try {
            List<IngredientCategory> item = ingredientService.findIngredientCategoryByResId(id);
            return new ResponseData<>(HttpStatus.OK.value(), "Get restaurant ingredient category successfully", item);
        } catch (Exception e) {
            log.error("Error = {} ", e.getMessage(), e.getCause());
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), "Get restaurant ingredient category failed");
        }
    }
}
