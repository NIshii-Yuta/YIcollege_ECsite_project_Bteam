package com.college.yi.ecsite.admin.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.college.yi.ecsite.admin.dto.AdminProductListDto;
import com.college.yi.ecsite.admin.repositoty.CategoryMapper;
import com.college.yi.ecsite.admin.service.AdminProductService;
import com.college.yi.ecsite.entity.Category;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class AdminProductController {

    private final AdminProductService adminProductService;
    private final CategoryMapper categoryMapper;
    private static final int PAGE_SIZE = 15;

    // 商品一覧
    @GetMapping("/admin/products")
    public String showProductList(@RequestParam(name = "page", defaultValue = "1") int page, Model model) {
        var productPage = adminProductService.getProductList(page, PAGE_SIZE);
        model.addAttribute("productPage", productPage);
        return "admin/products";
    }

    // 新規登録フォーム表示
    @GetMapping("/admin/products/new")
    public String showCreateForm(Model model) {
        model.addAttribute("productForm", new AdminProductListDto());
        // カテゴリリストもセット
        List<Category> categories = categoryMapper.findAll();
        model.addAttribute("categories", categories);
        return "admin/product_form";
    }

    // 編集フォーム表示
    @GetMapping("/admin/products/{id}/edit")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        AdminProductListDto dto = adminProductService.getProductById(id);
        model.addAttribute("productForm", dto);
        List<Category> categories = categoryMapper.findAll();
        model.addAttribute("categories", categories);
        return "admin/product_form";
    }
}
