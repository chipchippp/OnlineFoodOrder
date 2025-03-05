package com.example.OnlineFoodOrdering.service;

import com.example.OnlineFoodOrdering.model.*;
import com.example.OnlineFoodOrdering.repository.AddressRepository;
import com.example.OnlineFoodOrdering.repository.OrderItemRepository;
import com.example.OnlineFoodOrdering.repository.OrderRepository;
import com.example.OnlineFoodOrdering.repository.UserRepository;
import com.example.OnlineFoodOrdering.dto.request.OrderRequest;
import com.example.OnlineFoodOrdering.service.impl.CartService;
import com.example.OnlineFoodOrdering.service.impl.OrderService;
import com.example.OnlineFoodOrdering.service.impl.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final AddressRepository addressRepository;
    private final UserRepository userRepository;
    private final RestaurantService restaurantService;
    private final CartService cartService;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, OrderItemRepository orderItemRepository, AddressRepository addressRepository, UserRepository userRepository, RestaurantService restaurantService, CartService cartService) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
        this.restaurantService = restaurantService;
        this.cartService = cartService;
    }

    @Override
    public Order createOrder(OrderRequest order, UserEntity userEntity) throws Exception {
        Address shippAddress = order.getDeliveryAddress();
        Address saveAddress = addressRepository.save(shippAddress);

        if (!userEntity.getAddresses().contains(saveAddress)) {
            userEntity.getAddresses().add(saveAddress);
            userRepository.save(userEntity);
        }

        Restaurant restaurant = restaurantService.findRestaurantById(order.getRestaurantId());
        Order newOrder = new Order();
        newOrder.setCustomer(userEntity);
        newOrder.setRestaurant(restaurant);
        newOrder.setCreatedAt(new Date());
        newOrder.setOrderStatus("PENDING");
        newOrder.setDeliveryAddress(saveAddress);

        Cart cart = cartService.findCartByUserId(userEntity.getId());
        List<OrderItem> orderItems = new ArrayList<>();
        for (CartItem item : cart.getCartItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setFood(item.getFood());
            orderItem.setQuantity(item.getQuantity());
            orderItem.setIngredients(item.getIngredients());
            orderItem.setTotalPrice(item.getTotalPrice());

            OrderItem savedOrderItem = orderItemRepository.save(orderItem);
            orderItems.add(savedOrderItem);
        }
        Long totalPrice = cartService.calculateTotalPrice(cart);

        newOrder.setOrderItems(orderItems);
        newOrder.setTotalPrice(totalPrice);

        Order savedOrder = orderRepository.save(newOrder);
        restaurant.getOrders().add(savedOrder);

        return newOrder;
    }

    @Override
    public Order updateOrder(Long orderId, String orderStatus) throws Exception {
        Order order = getOrderById(orderId);
        if (orderStatus.equals("OUT_FOR_DELIVERY")
                || orderStatus.equals("DELIVERED")
                || orderStatus.equals("COMPLETED")
                || orderStatus.equals("PENDING")
                || orderStatus.equals("CANCELLED")) {
            order.setOrderStatus(orderStatus);
            return orderRepository.save(order);
        }
        throw new Exception("Invalid order status");
    }

    @Override
    public void cancelOrder(Long orderId) throws Exception {
        Order order = getOrderById(orderId);
        orderRepository.deleteById(orderId);
    }

    @Override
    public List<Order> getOrdersByUserId(Long userId) throws Exception {
        return orderRepository.findByCustomerId(userId);
    }

    @Override
    public List<Order> getOrdersByRestaurantId(Long restaurantId, String orderStatus) throws Exception {
        List<Order> orders = orderRepository.findByRestaurantId(restaurantId);
        if (orderStatus != null) {
            orders = orders.stream().filter(order -> order.getOrderStatus().equals(orderStatus)).toList();
        }
        return orders;
    }

    @Override
    public Order getOrderById(Long orderId) throws Exception {
        Optional<Order> order = orderRepository.findById(orderId);
        if (order.isEmpty()) {
            throw new Exception("Order not found");
        }
        return order.get();
    }
}
