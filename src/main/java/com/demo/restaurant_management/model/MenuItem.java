package com.demo.restaurant_management.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "menu_items")
public class MenuItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String description;
    private Float price;

    @OneToMany(mappedBy = "menuItem")
    private List<Image> images;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}
