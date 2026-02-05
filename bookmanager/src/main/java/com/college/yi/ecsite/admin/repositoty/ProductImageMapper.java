package com.college.yi.ecsite.admin.repositoty;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.college.yi.ecsite.entity.ProductImage;

@Mapper
public interface ProductImageMapper {

    // 商品のメイン画像を1件取得
    @Select("SELECT * FROM product_images WHERE product_id = #{productId} AND is_main = true LIMIT 1")
    ProductImage findMainImageByProductId(Long productId);

    // 商品画像を登録（sort_orderは常に1、is_mainはtrue）
    @Insert("""
        INSERT INTO product_images (product_id, image_url, sort_order, is_main)
        VALUES (#{productId}, #{imageUrl}, 1, true)
        """)
    void insert(ProductImage image);

    // 商品IDに紐づく画像を全削除（画像差し替えのため）
    @Delete("DELETE FROM product_images WHERE product_id = #{productId}")
    void deleteByProductId(Long productId);
}
