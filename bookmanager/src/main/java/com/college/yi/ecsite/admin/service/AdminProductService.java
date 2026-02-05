package com.college.yi.ecsite.admin.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.college.yi.ecsite.admin.dto.AdminProductListDto;
import com.college.yi.ecsite.admin.repositoty.CategoryMapper;
import com.college.yi.ecsite.admin.repositoty.ProductImageMapper;
import com.college.yi.ecsite.admin.repositoty.ProductMapper;
import com.college.yi.ecsite.entity.Product;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminProductService {

    private final ProductMapper productMapper;
    private final CategoryMapper categoryMapper;
    private final ProductImageMapper productImageMapper;

    // 商品一覧取得（ページング）
    public Page<AdminProductListDto> getProductList(int page, int pageSize) {
        int offset = (page - 1) * pageSize;

        List<Product> products = productMapper.findAllNotDeleted(pageSize, offset);
        int total = productMapper.countAllNotDeleted();

        List<AdminProductListDto> dtoList = products.stream()
                .map(this::toAdminProductListDto)
                .collect(Collectors.toList());

        Pageable pageable = PageRequest.of(page - 1, pageSize); // 0始まりに注意
        return new PageImpl<>(dtoList, pageable, total);
    }

    // 商品ID指定で1件取得（編集フォーム用）
    public AdminProductListDto getProductById(Long id) {
        Product product = productMapper.findById(id);
        if (product == null) {
            // 商品が存在しない場合はnull（もしくは例外を投げてもOK）
            return null;
        }
        return toAdminProductListDto(product);
    }

    // --- 共通変換ロジック（Product→AdminProductListDto） ---
    private AdminProductListDto toAdminProductListDto(Product product) {
        AdminProductListDto dto = new AdminProductListDto();

        dto.setId(product.getProductId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());

        // カテゴリID/カテゴリ名
        dto.setCategoryId(product.getCategoryId());
        String categoryName = categoryMapper.findCategoryName(product.getCategoryId());
        dto.setCategoryName(categoryName);

        // 画像
        var mainImage = productImageMapper.findMainImageByProductId(product.getProductId());
        dto.setImageUrl(mainImage != null ? mainImage.getImageUrl() : null);

        // 価格
        dto.setPrice(product.getPrice() != null ? product.getPrice().intValue() : null);

        // 在庫
        dto.setStock(product.getStockQuantity());

        // ステータス
        dto.setStatus(product.getStatus() == 1 ? "ACTIVE" : "INACTIVE");

        // 日付
        dto.setCreatedAt(product.getCreatedAt());
        dto.setUpdatedAt(product.getUpdatedAt());

        return dto;
    }
}
