package com.example.OnlineFoodOrdering.service;

import com.example.OnlineFoodOrdering.model.IngredientCategory;
import com.example.OnlineFoodOrdering.model.IngredientItem;
import com.example.OnlineFoodOrdering.model.Restaurant;
import com.example.OnlineFoodOrdering.repository.IngredientCategoryRepository;
import com.example.OnlineFoodOrdering.repository.IngredientRepository;
import com.example.OnlineFoodOrdering.service.impl.IngredientsService;
import com.example.OnlineFoodOrdering.service.impl.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IngredientsServiceImpl implements IngredientsService {
    private final IngredientRepository ingredientRepository;
    private final IngredientCategoryRepository ingredientCategoryRepository;
    private final RestaurantService restaurantService;

    @Autowired
    public IngredientsServiceImpl(IngredientRepository ingredientRepository, IngredientCategoryRepository ingredientCategoryRepository, RestaurantService restaurantService) {
        this.ingredientRepository = ingredientRepository;
        this.ingredientCategoryRepository = ingredientCategoryRepository;
        this.restaurantService = restaurantService;
    }

    @Override
    public IngredientCategory saveIngredient(String name, Long resId) throws Exception {
        Restaurant restaurant = restaurantService.findRestaurantById(resId);
        IngredientCategory ingredientCategory = new IngredientCategory();
        ingredientCategory.setName(name);
        ingredientCategory.setRestaurant(restaurant);
        return ingredientCategoryRepository.save(ingredientCategory);
    }

    @Override
    public IngredientCategory findIngredientCategoryById(Long id) throws Exception {
        Optional<IngredientCategory> ingredientCategory = ingredientCategoryRepository.findById(id);
        if (ingredientCategory.isEmpty()){
            throw new Exception("Ingredient Category not found");
        }
        return ingredientCategory.get();
    }

    @Override
    public List<IngredientCategory> findIngredientCategoryByResId(Long id) throws Exception {
        Restaurant restaurant = restaurantService.findRestaurantById(id);
        return ingredientCategoryRepository.findByRestaurantId(restaurant.getId());
    }

    @Override
    public IngredientItem saveIngredientItem(String ingredientName, Long resId, Long categoryId) throws Exception {
        Restaurant restaurant = restaurantService.findRestaurantById(resId);
        IngredientCategory category = findIngredientCategoryById(categoryId);

        IngredientItem item = new IngredientItem();
        item.setName(ingredientName);
        item.setRestaurant(restaurant);
        item.setCategory(category);

        IngredientItem ingredient = ingredientRepository.save(item);
        category.getIngredientItems().add(ingredient);

        return ingredient;
    }

    @Override
    public List<IngredientItem> findResIngredient(Long resId) throws Exception {
        return ingredientRepository.findByRestaurantId(resId);
    }

    @Override
    public IngredientItem updateStock(Long id) throws Exception {
        Optional<IngredientItem> ingredientItem = ingredientRepository.findById(id);
        if (ingredientItem.isEmpty()){
            throw new Exception("Ingredient Item not found");
        }

        IngredientItem item = ingredientItem.get();
        item.setInStoke(!item.isInStoke());
        return ingredientRepository.save(item);
    }
}
