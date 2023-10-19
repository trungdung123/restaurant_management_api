package com.demo.restaurant_management.service;

import com.demo.restaurant_management.web.dto.CategoryDto;
import com.demo.restaurant_management.web.dto.request.CreateCategoryRequest;
import com.demo.restaurant_management.web.dto.request.UpdateCategoryRequest;

import java.util.List;

public interface CategoryService {
    List<CategoryDto> getAllCategories();

    CategoryDto createCategory(CreateCategoryRequest createCategoryRequest);

    CategoryDto updateCategory(Integer categoryId, UpdateCategoryRequest updateCategoryRequest);

    void deleteCategory(Integer categoryId);

    CategoryDto getCategoryById(Integer categoryId);
}
