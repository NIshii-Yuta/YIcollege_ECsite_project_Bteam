package com.college.yi.ecsite.admin.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class AdminProductListDto {
    private Long id;              // product.id
    private String name;          // product.name
    private Long categoryId;  
    private String categoryName;  // product.categoryName
    private String imageUrl;      // product.imageUrl
    private Integer price;        // product.price
    private Integer stock;        // product.stock
    private String status;        // product.status
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
