package com.example.order_management.service;

import com.example.order_management.model.Product;
import com.example.order_management.model.PurchaseOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface PurchaseOrderService {

    List<PurchaseOrder> getAll();

    PurchaseOrder create(PurchaseOrder purchaseOrder, String token);

    PurchaseOrder update(UUID id, PurchaseOrder purchaseOrder, String token);

    void deleteById(UUID id);

    PurchaseOrder getById(UUID id);

    Page<PurchaseOrder> getAllPagination(Pageable pageable);

    Page<PurchaseOrder> createPagination(PurchaseOrder purchaseOrder, Pageable pageable, String token);

    Page<PurchaseOrder> updatePagination(UUID id, PurchaseOrder purchaseOrder, Pageable pageable, String token);

    Page<PurchaseOrder> deleteByIdPagination(UUID id, Pageable pageable);

    Page<PurchaseOrder> getByIdPagination(UUID id, Pageable pageable);

}
