package com.example.order_management.service.impl;

import com.example.order_management.model.Product;
import com.example.order_management.model.exception.ProductNotFoundException;
import com.example.order_management.repository.ProductRepository;
import com.example.order_management.service.ProductService;
import com.example.order_management.service.UserService;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.math.BigDecimal;
import java.util.*;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    UserService userService;

    @Override
    public List<Product> getAll() {

        return productRepository.findAll();

    }

    @Override
    public Product create(Product product) {
        ProductValidator.validateProduct(product);
        return productRepository.save(product);
    }

    @Override
    public Product update(UUID id, Product product) {
        try {
            Product existingProduct = getById(id);
            ProductValidator.validateProduct(product);
            product.setId(existingProduct.getId());
            return productRepository.save(product);
        } catch (ProductNotFoundException e) {
            throw new IllegalArgumentException("Produto não encontrado");
        }
    }

    @Override
    public void deleteById(UUID id) {
        try {
            Product product = getById(id);
            productRepository.deleteById(product.getId());
        } catch (ProductNotFoundException exception) {
            throw new ProductNotFoundException("Produto não encontrado");
        }
    }

    @Override
    public Product getById(UUID id) {

        return productRepository.findById(id).orElseThrow(ProductNotFoundException::new);

    }


    @Override
    public Page<Product> getAllPagination(Pageable pageable) {

        return productRepository.findAll(pageable);

    }

    @Override
    public Page<Product> createPagination(Product product, Pageable pageable) {
        ProductValidator.validateProduct(product);
        Product savedProduct = productRepository.save(product);
        List<Product> productList = Collections.singletonList(savedProduct);
        return new PageImpl<>(productList, pageable, 1);
    }

    @Override
    public Page<Product> updatePagination(UUID id, Product product, Pageable pageable) {
        try {
            Product existingProduct = getById(id);
            ProductValidator.validateProduct(product);
            product.setId(existingProduct.getId());
            Product savedProduct = productRepository.save(product);
            List<Product> productList = Collections.singletonList(savedProduct);
            return new PageImpl<>(productList, pageable, 1);

        } catch (ProductNotFoundException e) {
            throw new IllegalArgumentException("Produto não encontrado");
        }
    }

    @Override
    public Page<Product> deleteByIdPagination(UUID id, Pageable pageable) {
        Product product = productRepository.findById(id).orElseThrow(ProductNotFoundException::new);
        productRepository.deleteById(id);
        return new PageImpl<>(Collections.singletonList(product), pageable, 1);
    }

    @Override
    public Page<Product> getByIdPagination(UUID id, Pageable pageable) {
        Product product = productRepository.findById(id).orElseThrow(ProductNotFoundException::new);
        return new PageImpl<>(Collections.singletonList(product), pageable, 1);
    }

}