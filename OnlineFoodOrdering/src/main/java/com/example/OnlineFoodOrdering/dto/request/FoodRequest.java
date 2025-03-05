package com.example.OnlineFoodOrdering.dto.request;

import com.example.OnlineFoodOrdering.model.Category;
import com.example.OnlineFoodOrdering.model.IngredientItem;
import lombok.Data;

import java.util.List;

@Data
public class FoodRequest {
    private String name;
    private String description;
    private Long price;
    private Category category;
    private List<String> images;
    private Long restaurantId;
    private boolean vegetarian;
    private boolean seasonal;
    private List<IngredientItem> ingredients;

}
