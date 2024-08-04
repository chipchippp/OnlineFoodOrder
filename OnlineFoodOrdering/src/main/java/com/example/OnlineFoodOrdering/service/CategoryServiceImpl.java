package com.example.OnlineFoodOrdering.service;

import com.example.OnlineFoodOrdering.model.Category;
import com.example.OnlineFoodOrdering.model.Restaurant;
import com.example.OnlineFoodOrdering.repository.CategoryRepository;
import com.example.OnlineFoodOrdering.repository.RestaurantRepository;
import com.example.OnlineFoodOrdering.service.impl.CategoryService;
import com.example.OnlineFoodOrdering.service.impl.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final RestaurantService restaurantService;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, RestaurantService restaurantService) {
        this.categoryRepository = categoryRepository;
        this.restaurantService = restaurantService;
    }

    @Override
    public Category saveCategory(String category, Long userId) throws Exception {
        Restaurant restaurant = restaurantService.getRestaurantByUserId(userId);
        Category newCategory = new Category();
        newCategory.setName(category);
        newCategory.setRestaurant(restaurant);

        return categoryRepository.save(newCategory);
    }

    @Override
    public List<Category> findCategoryByResId(Long id) throws Exception {
        Restaurant restaurant = restaurantService.getRestaurantByUserId(id);
        return categoryRepository.findByRestaurantId(restaurant.getId());
    }

    @Override
    public Category findCategoryById(Long id) throws Exception {
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isEmpty()){
            throw new Exception("Category not found");
        }
        return category.get();
    }
}
