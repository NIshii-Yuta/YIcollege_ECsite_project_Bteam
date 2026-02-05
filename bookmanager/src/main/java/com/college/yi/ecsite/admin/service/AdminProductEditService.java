package com.college.yi.ecsite.admin.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.college.yi.ecsite.admin.dto.ProductEditDto;
import com.college.yi.ecsite.admin.form.ProductEditForm;
import com.college.yi.ecsite.admin.repositoty.ProductImageMapper;
import com.college.yi.ecsite.admin.repositoty.ProductMapper;
import com.college.yi.ecsite.entity.Category;
import com.college.yi.ecsite.entity.Product;
import com.college.yi.ecsite.entity.ProductImage;
import com.college.yi.ecsite.exception.ProductNotFoundException;

@Service
public class AdminProductEditService {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ProductImageMapper productImageMapper;

    // 編集画面に表示するデータを取得
    public ProductEditDto getEditData(Long productId) {
        Product product = productMapper.findById(productId);
        if (product == null) {
            throw new ProductNotFoundException("商品が見つかりません: ID = " + productId);
        }

        ProductImage image = productImageMapper.findMainImageByProductId(productId);

        ProductEditDto dto = new ProductEditDto();
        dto.setProductId(product.getProductId());
        dto.setName(product.getName());
        dto.setPrice(product.getPrice().intValue());
        dto.setCategoryId(product.getCategoryId());
        dto.setDescription(product.getDescription());
        dto.setStatus(product.getStatus() == 1); // 1=公開
        dto.setImageUrl(image != null ? image.getImageUrl() : null);
        return dto;
    }

    // DTO → Form変換
    public ProductEditForm convertToForm(ProductEditDto dto) {
        ProductEditForm form = new ProductEditForm();
        form.setName(dto.getName());
        form.setPrice(dto.getPrice());
        form.setCategoryId(dto.getCategoryId());
        form.setDescription(dto.getDescription());
        form.setStatus(dto.getStatus());
        return form;
    }

    // 商品更新処理
    public void updateProduct(Long productId, ProductEditForm form) {
        Product product = productMapper.findById(productId);
        if (product == null) {
            throw new ProductNotFoundException("商品が見つかりません: ID = " + productId);
        }

        // 商品情報の更新
        product.setName(form.getName());
        product.setPrice(BigDecimal.valueOf(form.getPrice()));
        product.setCategoryId(form.getCategoryId());
        product.setDescription(form.getDescription());
        product.setStatus(form.getStatus() ? (short) 1 : (short) 3); // trueなら販売中(1)、falseなら一時停止(3)

        productMapper.update(product);

        // 画像更新処理（あれば）
        MultipartFile image = form.getImage();
        if (image != null && !image.isEmpty()) {
            validateImage(image);
            String fileName = saveImage(image);
            productImageMapper.deleteByProductId(productId);

            ProductImage newImage = new ProductImage();
            newImage.setProductId(productId);
            newImage.setImageUrl(fileName);
            newImage.setSortOrder(1);
            newImage.setIsMain(true);
            productImageMapper.insert(newImage);
        }
    }

    // 画像を保存してファイル名を返す（※ファイル保存は今回は省略）
    private String saveImage(MultipartFile image) {
        // 拡張子取得
        String originalFilename = image.getOriginalFilename();
        String ext = originalFilename.substring(originalFilename.lastIndexOf(".") + 1).toLowerCase();

        // ファイル名生成
        return UUID.randomUUID().toString() + "." + ext;
    }

    // カテゴリ一覧取得（セレクトボックス用）
    public List<Category> getAllCategories() {
        return productMapper.findAllCategories();
    }

    private void validateImage(MultipartFile image) {
        // ファイル名チェック
        String fileName = image.getOriginalFilename();
        if (fileName == null || !fileName.matches("(?i).*\\.(jpg|jpeg|png|gif)$")) {
            throw new IllegalArgumentException("画像ファイルは jpg / jpeg / png / gif のいずれかにしてください");
        }

        // 容量チェック（5MBまで）
        long maxSize = 5 * 1024 * 1024; // 5MB
        if (image.getSize() > maxSize) {
            throw new IllegalArgumentException("画像サイズは5MB以下にしてください");
        }
    }
}
