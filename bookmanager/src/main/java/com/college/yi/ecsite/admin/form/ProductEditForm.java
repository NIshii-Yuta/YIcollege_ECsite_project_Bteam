package com.college.yi.ecsite.admin.form;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class ProductEditForm {

    @NotBlank(message = "商品名を入力してください")
    @Size(max = 50, message = "商品名は50文字以内で入力してください")
    private String name;

    @NotNull(message = "価格を入力してください")
    @Min(value = 0, message = "価格は0円以上で入力してください")
    @Max(value = 1000000, message = "価格は100万円未満で入力してください")
    private Integer price;

    @NotNull(message = "カテゴリを選択してください")
    private Long categoryId;

    @Size(max = 1000, message = "説明は1000文字以内で入力してください")
    private String description;

    private MultipartFile image;

    private Boolean status; // 公開ステータス（true:公開, false:非公開）
}
