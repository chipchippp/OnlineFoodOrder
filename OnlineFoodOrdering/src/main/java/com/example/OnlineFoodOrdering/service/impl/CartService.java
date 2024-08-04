package com.example.OnlineFoodOrdering.service.impl;

import com.example.OnlineFoodOrdering.model.Cart;
import com.example.OnlineFoodOrdering.model.CartItem;
import com.example.OnlineFoodOrdering.request.AddCartItemRequest;

public interface CartService {
    public CartItem addToCart(AddCartItemRequest req, String jwt) throws Exception;
    public CartItem updateCartItemQuantity(Long cartItemId, int quantity) throws Exception;
    public Cart removeCartItem(Long cartItemId, String jwt) throws Exception;
    public Long calculateTotalPrice(Cart cart) throws Exception;
    public Cart findCartById(Long id) throws Exception;
    public Cart findCartByUserId(String jwt) throws Exception;
    public Cart clearCart(String jwt) throws Exception;

}
