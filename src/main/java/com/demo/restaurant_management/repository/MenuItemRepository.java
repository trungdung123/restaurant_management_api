package com.demo.restaurant_management.repository;

import com.demo.restaurant_management.model.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface MenuItemRepository extends JpaRepository<MenuItem, Integer>, JpaSpecificationExecutor<MenuItem> {
    List<MenuItem> findByCategory_Id(Integer categoryId);
}
