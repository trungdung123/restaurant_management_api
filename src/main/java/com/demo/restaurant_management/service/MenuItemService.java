package com.demo.restaurant_management.service;

import com.demo.restaurant_management.web.dto.MenuItemDto;
import com.demo.restaurant_management.web.dto.request.CreateMenuItemRequest;
import com.demo.restaurant_management.web.dto.request.MenuItemCriteria;
import com.demo.restaurant_management.web.dto.request.UpdateMenuItemRequest;
import com.demo.restaurant_management.web.dto.request.utils.PagingRequest;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MenuItemService {
//    Page<MenuItemDto> getAllMenuItems(MenuItemCriteria menuItemCriteria);

    MenuItemDto getMenuItemById(Integer menuItemId);

    MenuItemDto createMenuItem(CreateMenuItemRequest createMenuItemRequest, MultipartFile[] files);

    MenuItemDto updateMenuItem(Integer menuItemId, UpdateMenuItemRequest menuItemRequest);

    void deleteMenuItem(Integer menuItemId);

    List<MenuItemDto> getAllMenuItems();

    List<MenuItemDto> getMenuItemOfCategory(Integer categoryId);
}
