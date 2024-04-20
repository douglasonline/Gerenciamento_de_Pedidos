package com.example.order_management.service.impl;

import com.example.order_management.model.OrderItem;
import com.example.order_management.model.Product;
import com.example.order_management.model.PurchaseOrder;
import com.example.order_management.model.User;
import com.example.order_management.model.exception.ProductNotFoundException;
import com.example.order_management.model.exception.PurchaseOrderNotFoundException;
import com.example.order_management.repository.OrderItemRepository;
import com.example.order_management.repository.PurchaseOrderRepository;
import com.example.order_management.repository.UserRepository;
import com.example.order_management.service.ProductService;
import com.example.order_management.service.PurchaseOrderService;
import com.example.order_management.service.UserService;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class PurchaseOrderImpl implements PurchaseOrderService {

    @Autowired
    PurchaseOrderRepository purchaseOrderRepository;

    @Autowired
    ProductService productService;

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    OrderItemRepository orderItemRepository;

    @Override
    public List<PurchaseOrder> getAll() {

        return purchaseOrderRepository.findAll();

    }

    @Override
    public PurchaseOrder create(PurchaseOrder purchaseOrder, String token) {
        if (purchaseOrder.getItems() == null || purchaseOrder.getItems().isEmpty()) {
            throw new IllegalArgumentException("A lista de 'items' não pode estar vazia.");
        }

        BigDecimal totalPrice = BigDecimal.ZERO;
        List<OrderItem> validOrderItems = new ArrayList<>();

        for (OrderItem item : purchaseOrder.getItems()) {
            if (item.getProduct() == null) {
                throw new IllegalArgumentException("O campo 'product' no item " + item.getId() + " não pode estar vazio.");
            }
            if (item.getQuantity() <= 0) {
                throw new IllegalArgumentException("A quantidade no item " + item.getId() + " deve ser maior que zero.");
            }

            Product product;
            try {
                product = productService.getById(item.getProduct().getId());
            } catch (ProductNotFoundException exception) {
                throw new IllegalArgumentException("O produto com ID " + item.getProduct().getId() + " não foi encontrado.");
            }

            OrderItem validOrderItem = new OrderItem();
            validOrderItem.setProduct(product);
            validOrderItem.setQuantity(item.getQuantity());
            validOrderItems.add(validOrderItem);

            BigDecimal itemPrice = product.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
            totalPrice = totalPrice.add(itemPrice);
        }

        validOrderItems = orderItemRepository.saveAll(validOrderItems);
        purchaseOrder.setUser(userRepository.findByEmailCustomQuery(userService.extractEmailFromToken(token)));
        purchaseOrder.setItems(validOrderItems);
        purchaseOrder.setTotalPrice(totalPrice);

        return purchaseOrderRepository.save(purchaseOrder);
    }


    @Override
    public PurchaseOrder update(UUID id, PurchaseOrder purchaseOrder, String token) {

        try {

            PurchaseOrder existingPurchaseOrder = getById(id);

            if (purchaseOrder.getItems() == null || purchaseOrder.getItems().isEmpty()) {
                throw new IllegalArgumentException("A lista de 'items' não pode estar vazia.");
            }

            BigDecimal totalPrice = BigDecimal.ZERO;
            List<OrderItem> validOrderItems = new ArrayList<>();

            for (OrderItem item : purchaseOrder.getItems()) {
                if (item.getProduct() == null) {
                    throw new IllegalArgumentException("O campo 'product' no item " + item.getId() + " não pode estar vazio.");
                }
                if (item.getQuantity() <= 0) {
                    throw new IllegalArgumentException("A quantidade no item " + item.getId() + " deve ser maior que zero.");
                }

                Product product;
                try {
                    product = productService.getById(item.getProduct().getId());
                } catch (ProductNotFoundException exception) {
                    throw new IllegalArgumentException("O produto com ID " + item.getProduct().getId() + " não foi encontrado.");
                }

                OrderItem validOrderItem = new OrderItem();
                validOrderItem.setProduct(product);
                validOrderItem.setQuantity(item.getQuantity());
                validOrderItems.add(validOrderItem);

                BigDecimal itemPrice = product.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
                totalPrice = totalPrice.add(itemPrice);
            }

            purchaseOrder.setUser(userRepository.findByEmailCustomQuery(userService.extractEmailFromToken(token)));
            purchaseOrder.setItems(validOrderItems);
            purchaseOrder.setTotalPrice(totalPrice);

            purchaseOrder.setId(existingPurchaseOrder.getId());
            return purchaseOrderRepository.save(purchaseOrder);

        } catch (PurchaseOrderNotFoundException e) {
            throw new IllegalArgumentException("Pedido não encontrado");
        }

    }

    @Override
    public void deleteById(UUID id) {

        purchaseOrderRepository.deleteById(id);

    }

    @Override
    public PurchaseOrder getById(UUID id) {

        return purchaseOrderRepository.findById(id).orElseThrow(PurchaseOrderNotFoundException::new);

    }

    @Override
    public Page<PurchaseOrder> getAllPagination(Pageable pageable) {

        return purchaseOrderRepository.findAll(pageable);

    }

    @Override
    public Page<PurchaseOrder> createPagination(PurchaseOrder purchaseOrder, Pageable pageable, String token) {

        if (purchaseOrder.getItems() == null || purchaseOrder.getItems().isEmpty()) {
            throw new IllegalArgumentException("A lista de 'items' não pode estar vazia.");
        }

        BigDecimal totalPrice = BigDecimal.ZERO;
        List<OrderItem> validOrderItems = new ArrayList<>();

        for (OrderItem item : purchaseOrder.getItems()) {
            if (item.getProduct() == null) {
                throw new IllegalArgumentException("O campo 'product' no item " + item.getId() + " não pode estar vazio.");
            }
            if (item.getQuantity() <= 0) {
                throw new IllegalArgumentException("A quantidade no item " + item.getId() + " deve ser maior que zero.");
            }

            Product product;
            try {
                product = productService.getById(item.getProduct().getId());
            } catch (ProductNotFoundException exception) {
                throw new IllegalArgumentException("O produto com ID " + item.getProduct().getId() + " não foi encontrado.");
            }

            OrderItem validOrderItem = new OrderItem();
            validOrderItem.setProduct(product);
            validOrderItem.setQuantity(item.getQuantity());
            validOrderItems.add(validOrderItem);

            BigDecimal itemPrice = product.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
            totalPrice = totalPrice.add(itemPrice);
        }

        purchaseOrder.setUser(userRepository.findByEmailCustomQuery(userService.extractEmailFromToken(token)));
        purchaseOrder.setItems(validOrderItems);
        purchaseOrder.setTotalPrice(totalPrice);

        PurchaseOrder savedPurchaseOrder = purchaseOrderRepository.save(purchaseOrder);
        List<PurchaseOrder> productList = Collections.singletonList(savedPurchaseOrder);
        return new PageImpl<>(productList, pageable, 1);
    }

    @Override
    public Page<PurchaseOrder> updatePagination(UUID id, PurchaseOrder purchaseOrder, Pageable pageable, String token) {

        try {

            PurchaseOrder existingPurchaseOrder = getById(id);

            if (purchaseOrder.getItems() == null || purchaseOrder.getItems().isEmpty()) {
                throw new IllegalArgumentException("A lista de 'items' não pode estar vazia.");
            }

            BigDecimal totalPrice = BigDecimal.ZERO;
            List<OrderItem> validOrderItems = new ArrayList<>();

            for (OrderItem item : purchaseOrder.getItems()) {
                if (item.getProduct() == null) {
                    throw new IllegalArgumentException("O campo 'product' no item " + item.getId() + " não pode estar vazio.");
                }
                if (item.getQuantity() <= 0) {
                    throw new IllegalArgumentException("A quantidade no item " + item.getId() + " deve ser maior que zero.");
                }

                Product product;
                try {
                    product = productService.getById(item.getProduct().getId());
                } catch (ProductNotFoundException exception) {
                    throw new IllegalArgumentException("O produto com ID " + item.getProduct().getId() + " não foi encontrado.");
                }

                OrderItem validOrderItem = new OrderItem();
                validOrderItem.setProduct(product);
                validOrderItem.setQuantity(item.getQuantity());
                validOrderItems.add(validOrderItem);

                BigDecimal itemPrice = product.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
                totalPrice = totalPrice.add(itemPrice);
            }

            purchaseOrder.setUser(userRepository.findByEmailCustomQuery(userService.extractEmailFromToken(token)));
            purchaseOrder.setItems(validOrderItems);
            purchaseOrder.setTotalPrice(totalPrice);

            purchaseOrder.setId(existingPurchaseOrder.getId());
            PurchaseOrder savedPurchaseOrder = purchaseOrderRepository.save(purchaseOrder);
            List<PurchaseOrder> purchaseOrderList = Collections.singletonList(savedPurchaseOrder);
            return new PageImpl<>(purchaseOrderList, pageable, 1);

        } catch (PurchaseOrderNotFoundException e) {
            throw new IllegalArgumentException("Pedido não encontrado");
        }

    }

    @Override
    public Page<PurchaseOrder> deleteByIdPagination(UUID id, Pageable pageable) {
        PurchaseOrder purchaseOrder = purchaseOrderRepository.findById(id).orElseThrow(PurchaseOrderNotFoundException::new);
        purchaseOrderRepository.deleteById(id);
        return new PageImpl<>(Collections.singletonList(purchaseOrder), pageable, 1);
    }

    @Override
    public Page<PurchaseOrder> getByIdPagination(UUID id, Pageable pageable) {
        // Recupera o produto com o ID especificado
        PurchaseOrder purchaseOrder = purchaseOrderRepository.findById(id).orElseThrow(PurchaseOrderNotFoundException::new);

        // Retorna uma página contendo o produto encontrado
        return new PageImpl<>(Collections.singletonList(purchaseOrder), pageable, 1);
    }

}
