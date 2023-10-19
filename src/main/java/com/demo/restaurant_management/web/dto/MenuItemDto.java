package com.demo.restaurant_management.web.dto;

import com.demo.restaurant_management.model.Image;
import lombok.Data;

import java.util.List;

@Data
public class MenuItemDto {
    private Integer id;
    private String name;
    private String description;
    private Float price;
    private List<Image> images;
    private CategoryDto categoryDto;
}
