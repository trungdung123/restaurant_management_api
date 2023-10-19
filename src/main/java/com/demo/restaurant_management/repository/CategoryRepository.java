package com.demo.restaurant_management.repository;

import com.demo.restaurant_management.model.Category;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    @EntityGraph(attributePaths = {"menuItems"})
    Optional<Category> findOneWithItemsById(Integer categoryId);
}
