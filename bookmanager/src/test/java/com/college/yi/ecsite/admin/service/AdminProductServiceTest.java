package com.college.yi.ecsite.admin.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;

import com.college.yi.ecsite.admin.dto.AdminProductListDto;
import com.college.yi.ecsite.admin.repositoty.CategoryMapper;
import com.college.yi.ecsite.admin.repositoty.ProductImageMapper;
import com.college.yi.ecsite.admin.repositoty.ProductMapper;
import com.college.yi.ecsite.entity.Product;
import com.college.yi.ecsite.entity.ProductImage;

class AdminProductServiceTest {

    @Mock
    private ProductMapper productMapper;
    @Mock
    private CategoryMapper categoryMapper;
    @Mock
    private ProductImageMapper productImageMapper;

    @InjectMocks
    private AdminProductService adminProductService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void 商品一覧を正常に取得できる() {
        // モックデータ
        Product product1 = createProduct(1L, "スマホ", 1L, 10000, 50, 1);
        Product product2 = createProduct(2L, "ノートPC", 2L, 200000, 10, 0);
        List<Product> productList = Arrays.asList(product1, product2);

        when(productMapper.findAllNotDeleted(10, 0)).thenReturn(productList);
        when(productMapper.countAllNotDeleted()).thenReturn(2);
        when(categoryMapper.findCategoryName(anyLong())).thenReturn("カテゴリ名");
        when(productImageMapper.findMainImageByProductId(anyLong())).thenReturn(new ProductImage(1L, 1L, "url.png",1, true,  LocalDateTime.now()));

        Page<AdminProductListDto> page = adminProductService.getProductList(1, 10);

        assertThat(page.getContent()).hasSize(2);
        assertThat(page.getContent().get(0).getName()).isEqualTo("スマホ");
        assertThat(page.getContent().get(1).getStatus()).isEqualTo("INACTIVE");
    }

    @Test
    void 存在しないIDを指定した場合nullを返す() {
        when(productMapper.findById(99L)).thenReturn(null);

        AdminProductListDto dto = adminProductService.getProductById(99L);

        assertThat(dto).isNull();
    }

    @Test
    void 商品IDで正常にDtoが取得できる() {
        Product product = createProduct(1L, "スマホ", 1L, 10000, 50, 1);

        when(productMapper.findById(1L)).thenReturn(product);
        when(categoryMapper.findCategoryName(anyLong())).thenReturn("モバイル");
        when(productImageMapper.findMainImageByProductId(1L)).thenReturn(new ProductImage(1L, 1L, "img.png", 1,true, LocalDateTime.now()));

        AdminProductListDto dto = adminProductService.getProductById(1L);

        assertThat(dto).isNotNull();
        assertThat(dto.getName()).isEqualTo("スマホ");
        assertThat(dto.getCategoryName()).isEqualTo("モバイル");
        assertThat(dto.getImageUrl()).isEqualTo("img.png");
        assertThat(dto.getStatus()).isEqualTo("ACTIVE");
    }

    // --- ヘルパーメソッド ---
    private Product createProduct(Long id, String name, Long categoryId, int price, int stock, int status) {
        Product p = new Product();
        p.setProductId(id);
        p.setName(name);
        p.setCategoryId(categoryId);
        p.setPrice(BigDecimal.valueOf(price));
        p.setStockQuantity(stock);
        p.setStatus((short)status);
        p.setCreatedAt(LocalDateTime.now());
        p.setUpdatedAt(LocalDateTime.now());
        return p;
    }
}
