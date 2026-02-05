package com.college.yi.ecsite.admin.repositoty;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.repository.query.Param;

import com.college.yi.ecsite.entity.Category;

@Mapper
public interface CategoryMapper {
    @Select("SELECT parent_category_id FROM categories WHERE category_id = #{categoryId}")
    Long findParentCategoryId(Long categoryId);
    
    @Select("SELECT name FROM categories WHERE category_id = #{categoryId}")
    String findCategoryName(@Param("categoryId") Long categoryId);
    
    @Select("SELECT category_id AS id, name FROM categories ORDER BY category_id")
    List<Category> findAll();
}
