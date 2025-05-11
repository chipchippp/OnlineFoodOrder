package com.example.OnlineFoodOrdering.service.impl;

import com.example.OnlineFoodOrdering.model.Category;

import java.util.List;

public interface CategoryService {
    public Category saveCategory(String category, Long userId) throws Exception;
//    public void deleteCategory(Long id) throws Exception;
//    public void updateCategory(Long id, String category) throws Exception;
    public List<Category> findCategoryByResId(Long id) throws Exception;
    public Category findCategoryById(Long id) throws Exception;
    public List<Category> findAllCategories() throws Exception;
}
