package com.demo.restaurant_management.repository;

import com.demo.restaurant_management.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ImageRepository extends JpaRepository<Image, UUID> {
}
