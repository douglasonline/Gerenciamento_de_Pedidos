package com.example.order_management.controller;

import com.example.order_management.model.Product;
import com.example.order_management.model.exception.ProductNotFoundException;
import com.example.order_management.model.exception.UnauthorizedException;
import com.example.order_management.service.ProductService;
import io.micrometer.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductService productService;

    Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

    @GetMapping("/all")
    public ResponseEntity<?> getAll() {

        List<Product> all = productService.getAll();
        LOGGER.info("OBTER TODOS OS PRODUTOS: " + all);
        return ResponseEntity.ok(all);


    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody Product product) {
        try {
            Product createdProduct = productService.create(product);
            LOGGER.info("CRIANDO PRODUTO: " + createdProduct);
            return ResponseEntity.ok(createdProduct);
        }  catch (IllegalArgumentException e) {
            LOGGER.error("Erro de validação: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody Product product) {
        try {
            Product updatedProduct = productService.update(UUID.fromString(id), product);
            LOGGER.info("ATUALIZANDO PRODUTO: " + updatedProduct);
            return ResponseEntity.ok(updatedProduct);
        } catch (ProductNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("Mensagem", "Produto não encontrado"));
        } catch (IllegalArgumentException e) {
            if (e.getMessage().startsWith("Invalid UUID string")) {
                LOGGER.error("O ID do produto fornecido é inválido.");
                String errorMessage = "O ID do produto fornecido é inválido.";
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("Mensagem", errorMessage));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("Mensagem", e.getMessage()));
            }
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteById(@PathVariable String id) {
        try {
            productService.deleteById(UUID.fromString(id));
            return ResponseEntity.status(HttpStatus.OK).body(Map.of("Mensagem", "Produto com o id " + id + " excluído com sucesso!"));
        } catch (ProductNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("Mensagem", "Produto não encontrado"));
        } catch (IllegalArgumentException e) {
            if (e.getMessage().startsWith("Invalid UUID string")) {
                LOGGER.error("O ID do produto fornecido é inválido.");
                String errorMessage = "O ID do produto fornecido é inválido.";
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("Mensagem", errorMessage));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("Mensagem", e.getMessage()));
            }
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable String id) {
        try {
            Product product = productService.getById(UUID.fromString(id));
            LOGGER.info("OBTER PRODUTO POR ID: " + id);
            return ResponseEntity.ok(product);
        } catch (ProductNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("Mensagem", "Produto não encontrado"));
        } catch (IllegalArgumentException e) {
            if (e.getMessage().startsWith("Invalid UUID string")) {
                LOGGER.error("O ID do produto fornecido é inválido.");
                String errorMessage = "O ID do produto fornecido é inválido.";
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("Mensagem", errorMessage));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("Mensagem", e.getMessage()));
            }
        }
    }

    @GetMapping("/allPagination")
    public ResponseEntity<?> getAllPagination(Pageable pageable) {

        Page<Product> product = productService.getAllPagination(pageable);
        LOGGER.info("OBTER TODOS OS PRODUTOS COM PAGINAÇÃO: " + product);
        return ResponseEntity.ok(product);

    }

    @PostMapping("/createPagination")
    public ResponseEntity<?> createPagination(@RequestBody Product product, Pageable pageable) {
        try {
            Page<Product> createdProductPage = productService.createPagination(product, pageable);
            LOGGER.info("CRIANDO PRODUTO COM PAGINAÇÃO: " + createdProductPage.getContent());
            return ResponseEntity.ok(createdProductPage);
        } catch (IllegalArgumentException e) {
            LOGGER.error("Erro de validação: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        }
    }


    @PutMapping("/updatePagination/{id}")
    public ResponseEntity<?> updatePagination(@PathVariable String id, @RequestBody Product product, Pageable pageable) {
        try {
            Product existingProduct = productService.getById(UUID.fromString(id));
            Page<Product> updatedPage = productService.updatePagination(existingProduct.getId(), product, pageable);
            LOGGER.info("ATUALIZANDO PRODUTO COM PAGINAÇÃO: " + updatedPage.getContent());
            return ResponseEntity.ok(updatedPage);
        } catch (ProductNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("Mensagem", "Produto não encontrado"));
        } catch (IllegalArgumentException e) {
            if (e.getMessage().startsWith("Invalid UUID string")) {
                LOGGER.error("O ID do produto fornecido é inválido.");
                String errorMessage = "O ID do produto fornecido é inválido.";
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("Mensagem", errorMessage));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("Mensagem", e.getMessage()));
            }
        }
    }

    @DeleteMapping("/deletePagination/{id}")
    public ResponseEntity<?> deleteByIdPagination(@PathVariable String id, Pageable pageable) {
        try {
            Product product = productService.getById(UUID.fromString(id));
            Page<Product> deletedProductPage = productService.deleteByIdPagination(product.getId(), pageable);
            LOGGER.info("PRODUTO COM O ID " + product.getId() + " EXCLUÍDO COM SUCESSO NA PAGINAÇÃO!");
            return ResponseEntity.status(HttpStatus.OK).body(Map.of("Mensagem", "Produto com o id " + product.getId() + " excluído com sucesso!", "Produto", deletedProductPage));
        } catch (ProductNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("Mensagem", "Produto não encontrado"));
        } catch (IllegalArgumentException e) {
            if (e.getMessage().startsWith("Invalid UUID string")) {
                LOGGER.error("O ID do produto fornecido é inválido.");
                String errorMessage = "O ID do produto fornecido é inválido.";
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("Mensagem", errorMessage));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("Mensagem", e.getMessage()));
            }
        }
    }

    @GetMapping("/pagination/{id}")
    public ResponseEntity<?> getByIdPagination(@PathVariable String id, Pageable pageable) {
        try {
            Page<Product> product = productService.getByIdPagination(UUID.fromString(id), pageable);
            LOGGER.info("OBTER PRODUTO POR ID COM PAGINAÇÃO: " + id);
            return ResponseEntity.ok(product);
        } catch (ProductNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("Mensagem", "Produto não encontrado"));
        } catch (IllegalArgumentException e) {
            if (e.getMessage().startsWith("Invalid UUID string")) {
                LOGGER.error("O ID do produto fornecido é inválido.");
                String errorMessage = "O ID do produto fornecido é inválido.";
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("Mensagem", errorMessage));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("Mensagem", e.getMessage()));
            }
        }
    }

}