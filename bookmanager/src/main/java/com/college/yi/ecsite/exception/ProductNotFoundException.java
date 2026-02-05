package com.college.yi.ecsite.exception;

public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException() {
        super("指定された商品が見つかりませんでした");
    }

    public ProductNotFoundException(String message) {
        super(message);
    }

    public ProductNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProductNotFoundException(Throwable cause) {
        super("指定された商品が見つかりませんでした", cause);
    }
}

