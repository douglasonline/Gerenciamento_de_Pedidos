package com.example.order_management.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "order_item") // Especifica o nome da tabela no banco de dados
@Getter
@Setter
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "purchase_order_id")
    private PurchaseOrder purchaseOrder;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private int quantity;

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                // ", purchaseOrder=" + (purchaseOrder != null ? purchaseOrder.getId() : null) +
                ", product=" + (product != null ? product.getName() : null) +
                ", quantity=" + quantity +
                '}';
    }

}
