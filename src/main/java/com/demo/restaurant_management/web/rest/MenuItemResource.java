package com.demo.restaurant_management.web.rest;

import com.demo.restaurant_management.service.MenuItemService;
import com.demo.restaurant_management.web.dto.request.CreateMenuItemRequest;
import com.demo.restaurant_management.web.dto.request.MenuItemCriteria;
import com.demo.restaurant_management.web.dto.request.UpdateMenuItemRequest;
import com.demo.restaurant_management.web.dto.request.utils.PagingRequest;
import com.demo.restaurant_management.web.dto.response.utils.ResponseUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/menu-items")
@RequiredArgsConstructor
public class MenuItemResource {
    private final MenuItemService menuItemService;

    @GetMapping
    public ResponseEntity<?> getAllMenuItems(MenuItemCriteria menuItemCriteria) {
        return ResponseUtils.ok(menuItemService.getAllMenuItems(menuItemCriteria));
    }

    @GetMapping("/{menuItemId}")
    public ResponseEntity<?> getMenuItemById(@PathVariable Integer menuItemId) {
        return ResponseUtils.ok(menuItemService.getMenuItemById(menuItemId));
    }

    @PostMapping
    public ResponseEntity<?> createMenuItem(@RequestPart(name = "data") @Valid CreateMenuItemRequest createMenuItemRequest, @RequestPart("files") MultipartFile[] files) {
        return ResponseUtils.ok(menuItemService.createMenuItem(createMenuItemRequest, files));
    }

    @PutMapping("/{menuItemId}")
    public ResponseEntity<?> updateMenuItem(@RequestBody @Valid UpdateMenuItemRequest menuItemRequest, @PathVariable Integer menuItemId) {
        return ResponseUtils.ok(menuItemService.updateMenuItem(menuItemId, menuItemRequest));
    }

    @DeleteMapping("/{menuItemId}")
    public ResponseEntity<?> deleteMenuItem(@PathVariable Integer menuItemId) {
        menuItemService.deleteMenuItem(menuItemId);
        return ResponseUtils.ok("Removed");
    }
}
