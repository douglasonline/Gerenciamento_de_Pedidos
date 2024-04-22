package com.example.order_management.service.impl;

import com.example.order_management.model.Product;
import io.micrometer.common.util.StringUtils;

import java.math.BigDecimal;

public class ProductValidator {

    public static void validateProduct(Product product) {
        validateProductName(product.getName());
        validateProductPrice(product.getPrice());
        validateProductCategory(product.getCategory());
    }

    private static void validateProductName(String name) {
        if (StringUtils.isEmpty(name)) {
            throw new IllegalArgumentException("O campo 'name' deve ser preenchido.");
        }
    }

    private static void validateProductPrice(BigDecimal price) {
        if (price == null || price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O campo 'price' deve ser um valor válido maior que zero.");
        }
    }

    private static void validateProductCategory(String category) {
        if (StringUtils.isEmpty(category)) {
            throw new IllegalArgumentException("O campo 'category' deve ser preenchido.");
        }
    }
}

