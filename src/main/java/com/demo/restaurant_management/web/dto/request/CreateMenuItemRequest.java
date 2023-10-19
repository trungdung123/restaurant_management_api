package com.demo.restaurant_management.web.dto.request;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class CreateMenuItemRequest {
    @NotBlank
    private String name;
    private String description;
    @NotNull
    @Min(1)
    private Float price;
    @NotNull
    private Integer categoryId;
}
