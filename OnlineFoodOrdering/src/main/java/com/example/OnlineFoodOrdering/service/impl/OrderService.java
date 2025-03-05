package com.example.OnlineFoodOrdering.service.impl;

import com.example.OnlineFoodOrdering.model.Order;
import com.example.OnlineFoodOrdering.model.UserEntity;
import com.example.OnlineFoodOrdering.dto.request.OrderRequest;

import java.util.List;

public interface OrderService {
    public Order createOrder(OrderRequest order, UserEntity userEntity) throws Exception;
    public Order updateOrder(Long orderId, String orderStatus) throws Exception;
    public void cancelOrder(Long orderId) throws Exception;
    public List<Order> getOrdersByUserId(Long userId) throws Exception;
    public List<Order> getOrdersByRestaurantId(Long restaurantId, String orderStatus) throws Exception;
    public Order getOrderById(Long orderId) throws Exception;
}
