package com.demo.restaurant_management.service.impl;

import com.demo.restaurant_management.model.Category;
import com.demo.restaurant_management.model.Image;
import com.demo.restaurant_management.model.MenuItem;
import com.demo.restaurant_management.repository.CategoryRepository;
import com.demo.restaurant_management.repository.ImageRepository;
import com.demo.restaurant_management.repository.MenuItemRepository;
import com.demo.restaurant_management.service.MenuItemService;
import com.demo.restaurant_management.service.utils.MappingHelper;
import com.demo.restaurant_management.web.dto.CategoryDto;
import com.demo.restaurant_management.web.dto.MenuItemDto;
import com.demo.restaurant_management.web.dto.request.CreateMenuItemRequest;
import com.demo.restaurant_management.web.dto.request.MenuItemCriteria;
import com.demo.restaurant_management.web.dto.request.UpdateMenuItemRequest;
import com.demo.restaurant_management.web.exception.EntityNotFoundException;
import com.demo.restaurant_management.web.rest.ImageResource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import javax.transaction.Transactional;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class MenuItemServiceImpl implements MenuItemService {
    private final MenuItemRepository menuItemRepository;
    private final CategoryRepository categoryRepository;
    private final ImageRepository imageRepository;
    private final MappingHelper mappingHelper;
    private final Path root = Paths.get("src/main/resources/static/image");

    @Override
    public Page<MenuItemDto> getAllMenuItems(MenuItemCriteria menuItemCriteria) {
        return menuItemRepository.findAll(
                menuItemCriteria.toSpecification(),
                menuItemCriteria.getPagingRequest().makePageable())
                .map(e -> {
                    var menuItemDto = mappingHelper.map(e, MenuItemDto.class);
                    menuItemDto.setCategoryDto(mappingHelper.map(e.getCategory(), CategoryDto.class));
                    return menuItemDto;
                });
    }

    @Override
    public MenuItemDto getMenuItemById(Integer menuItemId) {
        var menuItem = menuItemRepository.findById(menuItemId)
                .orElseThrow(() -> new EntityNotFoundException(MenuItem.class.getName(), menuItemId.toString()));

        var menuItemDto = mappingHelper.map(menuItem, MenuItemDto.class);
        menuItemDto.setCategoryDto(mappingHelper.map(menuItem.getCategory(), CategoryDto.class));
        return menuItemDto;
    }

    @Override
    @Transactional
    public MenuItemDto createMenuItem(CreateMenuItemRequest createMenuItemRequest, MultipartFile[] files) {
        var menuItem = mappingHelper.map(createMenuItemRequest, MenuItem.class);

        var category = categoryRepository.findById(createMenuItemRequest.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException(
                        Category.class.getName(), createMenuItemRequest.getCategoryId().toString()));
        menuItem.setCategory(category);
        menuItemRepository.save(menuItem);

        List<Image> images = new ArrayList<>();
        Arrays.stream(files).forEach(file -> {
            String fileName = file.getOriginalFilename();
            String extFile = Objects.requireNonNull(fileName).substring(fileName.lastIndexOf("."));

            Image image = new Image();
            image.setId(UUID.randomUUID());
            image.setTitle(image.getId().toString() + extFile);

            String url = MvcUriComponentsBuilder
                    .fromMethodName(ImageResource.class, "getFile", image.getTitle())
                    .build().toString();
            image.setImageUrl(url);

            try {
                Files.copy(file.getInputStream(), this.root.resolve(Objects.requireNonNull(image.getTitle())));
            } catch (Exception e) {
                if (e instanceof FileAlreadyExistsException) {
                    throw new RuntimeException("A file of that name already exists.");
                }
                throw new RuntimeException(e.getMessage());
            }
            image.setMenuItem(menuItem);
            images.add(image);
        });

        imageRepository.saveAll(images);
        menuItem.setImages(images);

        var menuItemDto = mappingHelper.map(menuItem, MenuItemDto.class);
        menuItemDto.setCategoryDto(mappingHelper.map(menuItem.getCategory(), CategoryDto.class));
        return menuItemDto;
    }

    @Override
    @Transactional
    public MenuItemDto updateMenuItem(Integer menuItemId, UpdateMenuItemRequest menuItemRequest) {
        var menuItem = menuItemRepository.findById(menuItemId)
                .orElseThrow(() -> new EntityNotFoundException(MenuItem.class.getName(), menuItemId.toString()));
        mappingHelper.mapIfSourceNotNullAndStringNotBlank(menuItemRequest, menuItem);

        var category = categoryRepository.findById(menuItemRequest.getCategoryId());
        category.ifPresent(menuItem::setCategory);
        menuItemRepository.save(menuItem);

        var menuItemDto = mappingHelper.map(menuItem, MenuItemDto.class);
        menuItemDto.setCategoryDto(mappingHelper.map(menuItem.getCategory(), CategoryDto.class));
        return menuItemDto;
    }

    @Override
    @Transactional
    public void deleteMenuItem(Integer menuItemId) {
        var menuItem = menuItemRepository.findById(menuItemId)
                .orElseThrow(() -> new EntityNotFoundException(MenuItem.class.getName(), menuItemId.toString()));
        menuItemRepository.deleteById(menuItemId);
    }
}
