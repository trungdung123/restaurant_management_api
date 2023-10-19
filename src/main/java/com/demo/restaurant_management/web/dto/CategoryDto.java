package com.demo.restaurant_management.web.dto;

import lombok.Data;

import java.util.List;

@Data
public class CategoryDto {
    private Integer id;
    private String name;
    private String description;
    private List<MenuItemDto> menuItemsDto;
}
