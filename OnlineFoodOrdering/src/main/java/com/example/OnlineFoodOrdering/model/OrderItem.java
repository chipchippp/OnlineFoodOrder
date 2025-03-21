package com.example.OnlineFoodOrdering.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "order_items")
public class OrderItem extends AbstractEntity<Long>{
    @ManyToOne
    private Food food;
    private int quantity;
    private Long totalPrice;
    private List<String> ingredients;
}
