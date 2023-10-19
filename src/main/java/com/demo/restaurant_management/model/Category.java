package com.demo.restaurant_management.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String description;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "category")
    private List<MenuItem> menuItems;
}
