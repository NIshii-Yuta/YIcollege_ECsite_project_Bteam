package com.college.yi.ecsite.admin.dto;

import lombok.Data;

@Data
public class ProductEditDto {

    private Long productId;       // 商品ID（表示のみ・編集不可）

    private String name;          // 商品名

    private Integer price;        // 価格（整数）

    private Long categoryId;      // カテゴリID

    private String description;   // 商品説明

    private String imageUrl;      // 表示用画像URL

    private Boolean status;       // 公開ステータス（true:公開, false:非公開）

}
