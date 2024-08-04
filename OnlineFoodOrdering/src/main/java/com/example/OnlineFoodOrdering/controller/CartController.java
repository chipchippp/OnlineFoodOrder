package com.example.OnlineFoodOrdering.controller;

import com.example.OnlineFoodOrdering.model.Cart;
import com.example.OnlineFoodOrdering.model.CartItem;
import com.example.OnlineFoodOrdering.model.UserEntity;
import com.example.OnlineFoodOrdering.request.AddCartItemRequest;
import com.example.OnlineFoodOrdering.request.UpdateCartItemRequest;
import com.example.OnlineFoodOrdering.service.impl.CartService;
import com.example.OnlineFoodOrdering.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cart")
public class CartController {
    private final CartService cartService;
    private final UserService userService;

    @Autowired
    public CartController(CartService cartService, UserService userService) {
        this.cartService = cartService;
        this.userService = userService;
    }

    @PostMapping("/add")
    public ResponseEntity<CartItem> saveCartItem(
            @RequestBody AddCartItemRequest req,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        CartItem cartItem = cartService.addToCart(req, jwt);
        return new ResponseEntity<>(cartItem, HttpStatus.CREATED);
    }

    @PutMapping("/cartItem/update/{cartItemId}")
    public ResponseEntity<CartItem> updateCartItem(
            @RequestBody UpdateCartItemRequest req,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        CartItem cartItem = cartService.updateCartItemQuantity(req.getCartItemId(), req.getQuantity());
        return new ResponseEntity<>(cartItem, HttpStatus.OK);
    }

    @DeleteMapping("/cartItem/remove/{id}")
    public ResponseEntity<?> removeCartItem(
            @PathVariable Long id,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
         Cart cart = cartService.removeCartItem(id, jwt);
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @PutMapping("/clear")
    public ResponseEntity<Cart> clearCart(
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        UserEntity user = userService.findByUserByJwtToken(jwt);
        Cart cart = cartService.clearCart(user.getId());
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @GetMapping("/clear")
    public ResponseEntity<Cart> findUserCart(
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        UserEntity user = userService.findByUserByJwtToken(jwt);
        Cart cart = cartService.findCartByUserId(user.getId());
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }
}
