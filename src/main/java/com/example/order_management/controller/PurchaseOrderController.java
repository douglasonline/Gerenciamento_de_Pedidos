package com.example.order_management.controller;

import com.example.order_management.model.OrderItem;
import com.example.order_management.model.Product;
import com.example.order_management.model.PurchaseOrder;
import com.example.order_management.model.exception.ProductNotFoundException;
import com.example.order_management.model.exception.PurchaseOrderNotFoundException;
import com.example.order_management.service.ProductService;
import com.example.order_management.service.PurchaseOrderService;
import io.micrometer.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/purchaseOrder")
public class PurchaseOrderController {

    @Autowired
    PurchaseOrderService purchaseOrderService;

    @Autowired
    ProductService productService;

    Logger LOGGER = LoggerFactory.getLogger(PurchaseOrderController.class);

    @GetMapping("/all")
    public ResponseEntity<?> getAll() {

        List<PurchaseOrder> all = purchaseOrderService.getAll();
        LOGGER.info("OBTER TODOS OS PRODUTOS: " + all);
        return ResponseEntity.ok(all);


    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody PurchaseOrder purchaseOrder, @RequestHeader("Authorization") String token) {
        try {
            PurchaseOrder createdPurchaseOrder = purchaseOrderService.create(purchaseOrder, token.replace("Bearer ", ""));
            LOGGER.info("CRIANDO PEDIDO: " + createdPurchaseOrder);
            return ResponseEntity.ok(createdPurchaseOrder);
        }  catch (IllegalArgumentException e) {
            LOGGER.error("Erro de validação: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        }

    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody PurchaseOrder purchaseOrder, @RequestHeader("Authorization") String token) {
        try {
            PurchaseOrder updatedPurchaseOrder = purchaseOrderService.update(UUID.fromString(id), purchaseOrder, token.replace("Bearer ", ""));
            LOGGER.info("ATUALIZANDO PEDIDO: " + updatedPurchaseOrder);
            return ResponseEntity.ok(updatedPurchaseOrder);
        } catch (PurchaseOrderNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("Mensagem", exception.getMessage()));
        } catch (ProductNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("Mensagem", "Pedido não encontrado"));
        } catch (IllegalArgumentException e) {
            if (e.getMessage().startsWith("Invalid UUID string")) {
                LOGGER.error("O ID do pedido fornecido é inválido.");
                String errorMessage = "O ID do pedido fornecido é inválido.";
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("Mensagem", errorMessage));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("Mensagem", e.getMessage()));
            }
        }
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteById(@PathVariable String id) {
        try {
            PurchaseOrder purchaseOrder = purchaseOrderService.getById(UUID.fromString(id));
            purchaseOrderService.deleteById(purchaseOrder.getId());
            LOGGER.info("PEDIDO COM O ID " + id + " EXCLUÍDO COM SUCESSO!");
            return ResponseEntity.status(HttpStatus.OK).body(Map.of("Mensagem", "Pedido com o id " + id + " excluido com sucesso!"));
        } catch (PurchaseOrderNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("Mensagem", "Pedido não encontrado"));
        } catch (IllegalArgumentException e) {
            if (e.getMessage().startsWith("Invalid UUID string")) {
                LOGGER.error("O ID do pedido fornecido é inválido.");
                String errorMessage = "O ID do pedido fornecido é inválido.";
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("Mensagem", errorMessage));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("Mensagem", e.getMessage()));
            }
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable String id) {
        try {
            PurchaseOrder purchaseOrder = purchaseOrderService.getById(UUID.fromString(id));
            LOGGER.info("OBTER PEDIDO POR ID: " + id);
            return ResponseEntity.ok(purchaseOrder);
        } catch (PurchaseOrderNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("Mensagem", "Pedido não encontrado"));
        } catch (IllegalArgumentException e) {
            if (e.getMessage().startsWith("Invalid UUID string")) {
                LOGGER.error("O ID do pedido fornecido é inválido.");
                String errorMessage = "O ID do pedido fornecido é inválido.";
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("Mensagem", errorMessage));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("Mensagem", e.getMessage()));
            }
        }
    }

    @GetMapping("/allPagination")
    public ResponseEntity<?> getAllPagination(Pageable pageable) {

        Page<PurchaseOrder> purchaseOrders = purchaseOrderService.getAllPagination(pageable);
        LOGGER.info("OBTER TODOS OS PRODUTOS COM PAGINAÇÃO: " + purchaseOrders);
        return ResponseEntity.ok(purchaseOrders);

    }

    @PostMapping("/createPagination")
    public ResponseEntity<?> createPagination(@RequestBody PurchaseOrder purchaseOrder, Pageable pageable, @RequestHeader("Authorization") String token) {
        try {
            Page<PurchaseOrder> createdPurchaseOrderPage = purchaseOrderService.createPagination(purchaseOrder, pageable, token.replace("Bearer ", ""));
            LOGGER.info("CRIANDO PEDIDO COM PAGINAÇÃO: " + createdPurchaseOrderPage.getContent());
            return ResponseEntity.ok(createdPurchaseOrderPage);
        } catch (IllegalArgumentException e) {
            LOGGER.error("Erro de validação: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        }
    }


    @PutMapping("/updatePagination/{id}")
    public ResponseEntity<?> updatePagination(@PathVariable String id, @RequestBody PurchaseOrder purchaseOrder, Pageable pageable, @RequestHeader("Authorization") String token) {
        try {
            Page<PurchaseOrder> updatedPage = purchaseOrderService.updatePagination(UUID.fromString(id), purchaseOrder, pageable, token.replace("Bearer ", ""));
            LOGGER.info("ATUALIZANDO PEDIDO COM PAGINAÇÃO: " + updatedPage.getContent());
            return ResponseEntity.ok(updatedPage);
        } catch (PurchaseOrderNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("Mensagem", exception.getMessage()));
        } catch (ProductNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("Mensagem", "Pedido não encontrado"));
        } catch (IllegalArgumentException e) {
            if (e.getMessage().startsWith("Invalid UUID string")) {
                LOGGER.error("O ID do pedido fornecido é inválido.");
                String errorMessage = "O ID do pedido fornecido é inválido.";
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("Mensagem", errorMessage));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("Mensagem", e.getMessage()));
            }
        }
    }

    @DeleteMapping("/deletePagination/{id}")
    public ResponseEntity<?> deleteByIdPagination(@PathVariable String id, Pageable pageable) {
        try {
            PurchaseOrder purchaseOrder = purchaseOrderService.getById(UUID.fromString(id));
            Page<PurchaseOrder> deletedpurchaseOrderPage = purchaseOrderService.deleteByIdPagination(purchaseOrder.getId(), pageable);
            LOGGER.info("PEDIDO COM O ID " + purchaseOrder.getId() + " EXCLUÍDO COM SUCESSO NA PAGINAÇÃO!");
            return ResponseEntity.status(HttpStatus.OK).body(Map.of("Mensagem", "Pedido com o id " + purchaseOrder.getId() + " excluído com sucesso!", "Pedido", deletedpurchaseOrderPage));
        } catch (PurchaseOrderNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("Mensagem", "Pedido não encontrado"));
        } catch (IllegalArgumentException e) {
            if (e.getMessage().startsWith("Invalid UUID string")) {
                LOGGER.error("O ID do pedido fornecido é inválido.");
                String errorMessage = "O ID do pedido fornecido é inválido.";
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("Mensagem", errorMessage));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("Mensagem", e.getMessage()));
            }
        }
    }

    @GetMapping("/pagination/{id}")
    public ResponseEntity<?> getByIdPagination(@PathVariable String id, Pageable pageable) {
        try {
            Page<PurchaseOrder> purchaseOrders = purchaseOrderService.getByIdPagination(UUID.fromString(id), pageable);
            LOGGER.info("OBTER PEDIDO POR ID COM PAGINAÇÃO: " + id);
            return ResponseEntity.ok(purchaseOrders);
        } catch (PurchaseOrderNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("Mensagem", "Pedido não encontrado"));
        } catch (IllegalArgumentException e) {
            if (e.getMessage().startsWith("Invalid UUID string")) {
                LOGGER.error("O ID do pedido fornecido é inválido.");
                String errorMessage = "O ID do pedido fornecido é inválido.";
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("Mensagem", errorMessage));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("Mensagem", e.getMessage()));
            }
        }
    }

}
