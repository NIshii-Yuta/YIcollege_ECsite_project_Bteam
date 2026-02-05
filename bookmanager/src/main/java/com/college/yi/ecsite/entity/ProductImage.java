package com.college.yi.ecsite.entity;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductImage {
    private Long imageId;
    private Long productId;
    private String imageUrl;
    private Integer sortOrder;
    private Boolean isMain;
    private LocalDateTime createdAt;
}
