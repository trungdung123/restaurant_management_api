package com.demo.restaurant_management.web.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CreateCategoryRequest {
    @NotBlank
    private String name;
    private String description;
}
