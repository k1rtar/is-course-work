package com.algoforge.taskservice.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CategoryID")
    private Long categoryId;

    @Column(name = "CategoryName", nullable = false)
    private String categoryName;

    @Column(name = "Description")
    private String description;
}
