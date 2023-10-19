package com.demo.restaurant_management.web.dto.request;

import lombok.Data;

import javax.validation.constraints.Min;

@Data
public class UpdateMenuItemRequest {
    private String name;
    private String description;
    @Min(1)
    private Float price;
    private Integer categoryId;
}
