package com.example.OnlineFoodOrdering.service;

import com.example.OnlineFoodOrdering.model.Category;
import com.example.OnlineFoodOrdering.model.Food;
import com.example.OnlineFoodOrdering.model.Restaurant;
import com.example.OnlineFoodOrdering.repository.FoodRepository;
import com.example.OnlineFoodOrdering.dto.request.FoodRequest;
import com.example.OnlineFoodOrdering.service.impl.CategoryService;
import com.example.OnlineFoodOrdering.service.impl.FoodService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class FoodServiceImpl implements FoodService {
    FoodRepository foodRepository;
    CategoryService categoryService;

    @Override
    public Food saveFood(FoodRequest foodRequest,
                         Restaurant restaurant
    ) throws Exception {

        Category category = categoryService.findCategoryById(foodRequest.getCategoryId());
        Food food = Food.builder()
                .name(foodRequest.getName())
                .category(category)
                .description(foodRequest.getDescription())
                .price(foodRequest.getPrice())
                .isVeg(foodRequest.isVegetarian())
                .isSeasonal(foodRequest.isSeasonal())
                .available(true)
                .restaurant(restaurant)
                .category(category)
                .build();

        Food saveFood = foodRepository.save(food);
        restaurant.getFoods().add(saveFood);
        return saveFood;
    }

    @Override
    public void deleteFood(Long id) throws Exception {
        Food food = findFoodById(id);
        food.setRestaurant(null);
        foodRepository.delete(food);
    }

    @Override
    public List<Food> getRestaurantFood(Long resId,
                                        boolean isVeg,
                                        boolean isSeasonal,
                                        boolean isNonVeg,
                                        String foodCategory
    ) throws Exception {

        List<Food> foods = foodRepository.findByRestaurantId(resId);
        if (isVeg) {
            foods = filterByVeg(foods, isVeg);
        }
        if (isNonVeg) {
            foods = filterByNonVeg(foods, isNonVeg);
        }
        if (isSeasonal) {
            foods = filterBySeasonal(foods, isSeasonal);
        }
        if (foodCategory != null && !foodCategory.equals("")) {
            foods = filterByCategory(foods, foodCategory);
        }
        return null;
    }

    private List<Food> filterByCategory(List<Food> foods, String foodCategory) {
        return foods.stream().filter(food -> {
            if (food.getCategory() != null) {
                return food.getCategory().getName().equals(foodCategory);
            } else {
                return false;
            }
                }).collect(Collectors.toList());
    }

    private List<Food> filterBySeasonal(List<Food> foods, boolean isSeasonal) {
        return foods.stream().filter(food -> food.isSeasonal()==isSeasonal).collect(Collectors.toList());
    }

    private List<Food> filterByNonVeg(List<Food> foods, boolean isNonVeg) {
        return foods.stream().filter(food -> food.isVeg()==false).collect(Collectors.toList());
    }

    private List<Food> filterByVeg(List<Food> foods, boolean isVeg) {
        return foods.stream().filter(food -> food.isVeg()==isVeg).collect(Collectors.toList());
    }


    @Override
    public List<Food> searchFood(String keyWord) throws Exception {
        return foodRepository.searchFood(keyWord);
    }

    @Override
    public Food findFoodById(Long id) throws Exception {
        Optional<Food> food = foodRepository.findById(id);

        if(food.isEmpty()) {
            throw new Exception("Food not exist");
        }
        return food.get();
    }

    @Override
    public Food updateAvailabilityStatus(Long id) throws Exception {
        Food food = findFoodById(id);
        food.setAvailable(!food.isAvailable());
        return foodRepository.save(food);
    }
}
