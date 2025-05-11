package com.example.OnlineFoodOrdering.controller;

import com.example.OnlineFoodOrdering.dto.response.*;
import com.example.OnlineFoodOrdering.model.*;
import com.example.OnlineFoodOrdering.dto.request.*;
import com.example.OnlineFoodOrdering.service.impl.*;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/")
public class OrderController {
    OrderService orderService;
    UserService userService;

    @PostMapping("/order/create")
    public ResponseData<Long> saveOrder(
            @Valid @RequestBody OrderRequest req,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        try {
            UserEntity user = userService.findByUserByJwtToken(jwt);
            orderService.createOrder(req, user);
            return new ResponseData<>(HttpStatus.OK.value(), "Order created successfully");
        } catch (Exception e) {
            log.error("Error = {} ", e.getMessage(), e.getCause());
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), "Create order failed");
        }
    }

    @GetMapping("/order/user")
    public ResponseData<?> getOrderHistory(
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        try {
            UserEntity user = userService.findByUserByJwtToken(jwt);
            List<Order> order = orderService.getOrdersByUserId(user.getId());
            return new ResponseData<>(HttpStatus.OK.value(), "Get order history successfully", order);
        } catch (Exception e) {
            log.error("Error = {} ", e.getMessage(), e.getCause());
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), "Get order history failed");
        }
    }

    @GetMapping("/order/admin/{id}")
    public ResponseData<?> getOrderAdminHistory(
            @PathVariable Long id,
            @RequestParam(required = false) String orderStatus,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        try {
            UserEntity user = userService.findByUserByJwtToken(jwt);
            List<Order> order = orderService.getOrdersByRestaurantId(id, orderStatus);
            return new ResponseData<>(HttpStatus.OK.value(), "Get order history successfully", order);
        } catch (Exception e) {
            log.error("Error = {} ", e.getMessage(), e.getCause());
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), "Get order history failed");
        }
    }

    @PostMapping("/order/{id}/{orderStatus}")
    public ResponseData<?> updateOrderStatus(
            @PathVariable Long id,
            @PathVariable String orderStatus,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        try {
            UserEntity user = userService.findByUserByJwtToken(jwt);
            orderService.updateOrder(id, orderStatus);
            return new ResponseData<>(HttpStatus.OK.value(), "Update order status successfully");
        } catch (Exception e) {
            log.error("Error = {} ", e.getMessage(), e.getCause());
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), "Update order status failed");
        }
    }

}
