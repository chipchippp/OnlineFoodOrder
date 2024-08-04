package com.example.OnlineFoodOrdering.request;

import com.example.OnlineFoodOrdering.model.Address;
import lombok.Data;

@Data
public class OrderRequest {
    private Long restaurantId;
    private Address deliveryAddress;
}
