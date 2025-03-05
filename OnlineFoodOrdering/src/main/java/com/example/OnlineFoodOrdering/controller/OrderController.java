package com.example.OnlineFoodOrdering.controller;

import com.example.OnlineFoodOrdering.model.Order;
import com.example.OnlineFoodOrdering.model.UserEntity;
import com.example.OnlineFoodOrdering.dto.request.OrderRequest;
import com.example.OnlineFoodOrdering.service.impl.OrderService;
import com.example.OnlineFoodOrdering.service.impl.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/")
public class OrderController {
    private final OrderService orderService;
    private final UserService userService;

    @PostMapping("/order/create")
    public ResponseEntity<Order> saveOrder(
            @RequestBody OrderRequest req,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        UserEntity user = userService.findByUserByJwtToken(jwt);
        Order order = orderService.createOrder(req, user);
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

    @GetMapping("/order/user")
    public ResponseEntity<List<Order>> getOrderHistory(
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        UserEntity user = userService.findByUserByJwtToken(jwt);
        List<Order> order = orderService.getOrdersByUserId(user.getId());
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @GetMapping("/order/admin/{id}")
    public ResponseEntity<List<Order>> getOrderAdminHistory(
            @PathVariable Long id,
            @RequestParam(required = false) String orderStatus,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        UserEntity user = userService.findByUserByJwtToken(jwt);
        List<Order> order = orderService.getOrdersByRestaurantId(id, orderStatus);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @PostMapping("/order/{id}/{orderStatus}")
    public ResponseEntity<Order> updateOrderStatus(
            @PathVariable Long id,
            @PathVariable String orderStatus,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        UserEntity user = userService.findByUserByJwtToken(jwt);
        Order order = orderService.updateOrder(id, orderStatus);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

}
