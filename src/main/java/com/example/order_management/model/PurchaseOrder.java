package com.example.order_management.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "purchase_order")
public class PurchaseOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "purchase_order_id")
    private List<OrderItem> items;

    private BigDecimal totalPrice;

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", user=" + user +
                ", items=" + items +
                ", totalPrice=" + totalPrice +
                '}';
    }

}

