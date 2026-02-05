package com.college.yi.ecsite.admin.repositoty;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.college.yi.ecsite.entity.Category;
import com.college.yi.ecsite.entity.Product;

@Mapper
public interface ProductMapper {

    @Select("SELECT * FROM products WHERE status != 4 ORDER BY product_id DESC LIMIT #{limit} OFFSET #{offset}")
    List<Product> findAllNotDeleted(@Param("limit") int limit, @Param("offset") int offset);

    @Select("SELECT COUNT(*) FROM products WHERE status != 4")
    int countAllNotDeleted();

    @Select("SELECT * FROM products WHERE product_id = #{productId} AND status != 4")
    Product findById(@Param("productId") Long productId);

    @Update("UPDATE product SET stock_quantity = #{stockQuantity}, updated_at = CURRENT_TIMESTAMP WHERE product_id = #{productId}")
    int updateStockQuantity(@Param("productId") Long productId, @Param("stockQuantity") Integer stockQuantity);

    @Insert("""
        INSERT INTO products (shop_id, category_id, name, description, price, tax_rate, stock_quantity, status)
        VALUES (1, #{categoryId}, #{name}, #{description}, #{price}, 10.0, 0, 1)
        RETURNING product_id""")
    @Options(useGeneratedKeys = true, keyProperty = "productId")
    void insert(Product product);

  

    @Update("""
        UPDATE products
        SET name = #{name},
            price = #{price},
            category_id = #{categoryId},
            description = #{description},
            status = #{status},
            updated_at = CURRENT_TIMESTAMP
        WHERE product_id = #{productId}
        """)
    void update(Product product);

    @Select("SELECT * FROM categories ORDER BY category_id ASC")
    List<Category> findAllCategories();
}
