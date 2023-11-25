package com.demo.restaurant_management.web.rest;

import com.demo.restaurant_management.service.CategoryService;
import com.demo.restaurant_management.web.dto.request.CreateCategoryRequest;
import com.demo.restaurant_management.web.dto.request.UpdateCategoryRequest;
import com.demo.restaurant_management.web.dto.response.utils.ResponseUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
@CrossOrigin("*")
public class CategoryResource {
    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<?> getAllCategories() {
        return ResponseUtils.ok(categoryService.getAllCategories());
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<?> getCategoryById(@PathVariable Integer categoryId) {
        return ResponseUtils.ok(categoryService.getCategoryById(categoryId));
    }

    @PostMapping
    public ResponseEntity<?> createCategory(@RequestBody @Valid CreateCategoryRequest createCategoryRequest) {
        return ResponseUtils.ok(categoryService.createCategory(createCategoryRequest));
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<?> updateCategory(@RequestBody @Valid UpdateCategoryRequest updateCategoryRequest, @PathVariable Integer categoryId) {
        return ResponseUtils.ok(categoryService.updateCategory(categoryId, updateCategoryRequest));
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<?> deleteCategory(@PathVariable Integer categoryId) {
        categoryService.deleteCategory(categoryId);
        return ResponseUtils.ok("Removed");
    }
}
