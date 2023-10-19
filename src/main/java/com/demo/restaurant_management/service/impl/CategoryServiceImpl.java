package com.demo.restaurant_management.service.impl;

import com.demo.restaurant_management.model.Category;
import com.demo.restaurant_management.repository.CategoryRepository;
import com.demo.restaurant_management.service.CategoryService;
import com.demo.restaurant_management.service.utils.MappingHelper;
import com.demo.restaurant_management.web.dto.CategoryDto;
import com.demo.restaurant_management.web.dto.MenuItemDto;
import com.demo.restaurant_management.web.dto.request.CreateCategoryRequest;
import com.demo.restaurant_management.web.dto.request.UpdateCategoryRequest;
import com.demo.restaurant_management.web.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final MappingHelper mappingHelper;

    @Override
    public List<CategoryDto> getAllCategories() {
        return categoryRepository.findAll()
                .stream().map(e -> mappingHelper.map(e, CategoryDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CategoryDto createCategory(CreateCategoryRequest createCategoryRequest) {
        var category = mappingHelper.map(createCategoryRequest, Category.class);
        categoryRepository.save(category);
        return mappingHelper.map(category, CategoryDto.class);
    }

    @Override
    @Transactional
    public CategoryDto updateCategory(Integer categoryId, UpdateCategoryRequest updateCategoryRequest) {
        var category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException(Category.class.getName(), categoryId.toString()));

        mappingHelper.mapIfSourceNotNullAndStringNotBlank(updateCategoryRequest, category);
        categoryRepository.save(category);
        return mappingHelper.map(category, CategoryDto.class);
    }

    @Override
    @Transactional
    public void deleteCategory(Integer categoryId) {
        categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException(Category.class.getName(), categoryId.toString()));
        categoryRepository.deleteById(categoryId);
    }

    @Override
    public CategoryDto getCategoryById(Integer categoryId) {
        var category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException(Category.class.getName(), categoryId.toString()));

        var categoryDto = mappingHelper.map(category, CategoryDto.class);
        categoryDto.setMenuItemsDto(mappingHelper.mapList(category.getMenuItems(), MenuItemDto.class));
        return categoryDto;
    }
}
