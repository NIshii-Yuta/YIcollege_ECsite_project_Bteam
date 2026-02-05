package com.college.yi.ecsite.exception;

public class ProductUpdateException extends RuntimeException {

    public ProductUpdateException() {
        super("商品情報の更新に失敗しました");
    }

    public ProductUpdateException(String message) {
        super(message);
    }

    public ProductUpdateException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProductUpdateException(Throwable cause) {
        super("商品情報の更新に失敗しました", cause);
    }
}
