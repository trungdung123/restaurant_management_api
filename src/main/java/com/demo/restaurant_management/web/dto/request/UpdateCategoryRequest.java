package com.demo.restaurant_management.web.dto.request;

import lombok.Data;

@Data
public class UpdateCategoryRequest {
    private String name;
    private String description;
}
