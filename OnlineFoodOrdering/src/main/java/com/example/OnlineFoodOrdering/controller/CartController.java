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

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/cart")
public class CartController {
    CartService cartService;
    UserService userService;

    @PostMapping("/add")
    public ResponseData<?> saveCartItem(
            @Valid @RequestBody AddCartItemRequest req,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        try {
            UserEntity user = userService.findByUserByJwtToken(jwt);
            CartItem cartItem = cartService.addToCart(req, jwt);
            return new ResponseData<>(HttpStatus.CREATED.value(), "Cart item added successfully", cartItem);
        } catch (Exception e) {
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }
    }

    @PutMapping("/cartItem/update/{cartItemId}")
    public ResponseData<?> updateCartItem(
            @Valid @RequestBody UpdateCartItemRequest req,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        try {
            UserEntity user = userService.findByUserByJwtToken(jwt);
            CartItem cartItem = cartService.updateCartItemQuantity(req.getCartItemId(), req.getQuantity());
            return new ResponseData<>(HttpStatus.OK.value(), "Cart item updated successfully", cartItem);
        } catch (Exception e) {
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }
    }

    @DeleteMapping("/cartItem/remove/{id}")
    public ResponseData<?> removeCartItem(
            @PathVariable Long id,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        try {
            UserEntity user = userService.findByUserByJwtToken(jwt);
            cartService.removeCartItem(id, jwt);
            return new ResponseData<>(HttpStatus.OK.value(), "Cart item removed successfully", null);
        } catch (Exception e) {
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }
    }

    @PutMapping("/clear")
    public ResponseData<?> clearCart(
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        try {
            UserEntity user = userService.findByUserByJwtToken(jwt);
            Cart cart = cartService.clearCart(user.getId());
            return new ResponseData<>(HttpStatus.OK.value(), "Cart cleared successfully", cart);
        } catch (Exception e) {
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }
    }

    @GetMapping("/clear")
    public ResponseData<Cart> findUserCart(
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        try {
            UserEntity user = userService.findByUserByJwtToken(jwt);
            Cart cart = cartService.findCartByUserId(user.getId());
            return new ResponseData<>(HttpStatus.OK.value(), "Cart found successfully", cart);
        } catch (Exception e) {
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }
    }
}
