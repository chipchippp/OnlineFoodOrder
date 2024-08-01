package com.example.OnlineFoodOrdering.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private UserEntity customer;
    @JsonIgnore
    @ManyToOne
    private Restaurant restaurant;
    private Long totalAmount;
    private String orderStatus;
    private Date createdAt;
    @ManyToOne
    private Address deliveryAddress;
    @OneToMany
    private List<OrderItem> orderItems;
//    private Payment payment;
    private int totalItems;
    private int totalPrice;
}
