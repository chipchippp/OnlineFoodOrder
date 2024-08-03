package com.example.OnlineFoodOrdering.request;

import lombok.Data;

@Data
public class IngredientRequest {
    private String name;
    private Long resId;
    private Long categoryId;
}
