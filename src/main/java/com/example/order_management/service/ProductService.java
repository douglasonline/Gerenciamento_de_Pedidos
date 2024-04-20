package com.example.order_management.service;

import com.example.order_management.model.Product;
import com.example.order_management.service.impl.ProductServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface ProductService {

    List<Product> getAll();

    Product create(Product product);

    Product update(UUID id, Product product);

    void deleteById(UUID id);

    Product getById(UUID id);

    Page<Product> getAllPagination(Pageable pageable);

    Page<Product> createPagination(Product product, Pageable pageable);

    Page<Product> updatePagination(UUID id, Product product, Pageable pageable);

    Page<Product> deleteByIdPagination(UUID id, Pageable pageable);

    Page<Product> getByIdPagination(UUID id, Pageable pageable);

}
