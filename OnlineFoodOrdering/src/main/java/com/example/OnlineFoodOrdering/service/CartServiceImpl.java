package com.example.OnlineFoodOrdering.service;

import com.example.OnlineFoodOrdering.exception.ResourceNotFoundException;
import com.example.OnlineFoodOrdering.model.Cart;
import com.example.OnlineFoodOrdering.model.CartItem;
import com.example.OnlineFoodOrdering.model.Food;
import com.example.OnlineFoodOrdering.model.UserEntity;
import com.example.OnlineFoodOrdering.repository.CartItemRepository;
import com.example.OnlineFoodOrdering.repository.CartRepository;
import com.example.OnlineFoodOrdering.repository.FoodRepository;
import com.example.OnlineFoodOrdering.dto.request.AddCartItemRequest;
import com.example.OnlineFoodOrdering.service.impl.CartService;
import com.example.OnlineFoodOrdering.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final UserService userService;
    private final CartItemRepository cartItemRepository;
    private final FoodRepository foodRepository;

    @Autowired
    public CartServiceImpl(CartRepository cartRepository, UserService userService, CartItemRepository cartItemRepository, FoodRepository foodRepository) {
        this.cartRepository = cartRepository;
        this.userService = userService;
        this.cartItemRepository = cartItemRepository;
        this.foodRepository = foodRepository;
    }

    @Override
    public CartItem addToCart(AddCartItemRequest req, String jwt) throws Exception {
        UserEntity user = userService.findByUserByJwtToken(jwt);
        Optional<Food> food = foodRepository.findById(req.getFoodId());
        Cart cart = cartRepository.findCartByCustomerId(user.getId());

        for (CartItem item : cart.getCartItems()) {
            if (item.getFood().equals(food.orElse(null))) {
                int newQuantity = item.getQuantity() + req.getQuantity();
                return updateCartItemQuantity(item.getId(), newQuantity);
            }
        }

        if (food.isEmpty()) {
            throw new ResourceNotFoundException("Food not found");
        }

        Food foodEntity = food.get();

        CartItem cartItem = new CartItem();
        cartItem.setFood(foodEntity);
        cartItem.setQuantity(req.getQuantity());
        cartItem.setCart(cart);
        cartItem.setIngredients(req.getIngredients());
        cartItem.setTotalPrice(foodEntity.getPrice() * req.getQuantity());

        CartItem savedCartItem = cartItemRepository.save(cartItem);
        cart.getCartItems().add(savedCartItem);
        return savedCartItem;
    }

    @Override
    public CartItem updateCartItemQuantity(Long cartItemId, int quantity) throws Exception {
        Optional<CartItem> cartItem = cartItemRepository.findById(cartItemId);
        if (cartItem.isEmpty()){
            throw new Exception("Cart item not found");
        }
        CartItem item = cartItem.get();
        item.setQuantity(quantity);
        item.setTotalPrice(item.getFood().getPrice() * quantity);
        return cartItemRepository.save(item);
    }

    @Override
    public Cart removeCartItem(Long cartItemId, String jwt) throws Exception {
        UserEntity user = userService.findByUserByJwtToken(jwt);
        Cart cart = cartRepository.findCartByCustomerId(user.getId());
        Optional<CartItem> cartItem = cartItemRepository.findById(cartItemId);

        if (cartItem.isEmpty()){
            throw new Exception("Cart item not found");
        }
        CartItem item = cartItem.get();
        cart.getCartItems().remove(item);

        return cartRepository.save(cart);
    }

    @Override
    public Long calculateTotalPrice(Cart cart) throws Exception {
        Long total = 0L;
        for (CartItem item : cart.getCartItems()) {
                total += item.getFood().getPrice() * item.getQuantity();
        }
        return total;
    }

    @Override
    public Cart findCartById(Long id) throws Exception {
        Cart cart = cartRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found with id: " + id));
        return cart;
    }

    @Override
    public Cart findCartByUserId(Long userId) throws Exception {
//        UserEntity user = userService.findByUserByJwtToken(userId);
        Cart cart = cartRepository.findCartByCustomerId(userId);
        cart.setTotal(calculateTotalPrice(cart));
        return cart;
    }

    @Override
    public Cart clearCart(Long userId) throws Exception {
//        UserEntity user = userService.findByUserByJwtToken(userId);
        Cart cart = findCartByUserId(userId);
        cart.getCartItems().clear();
        return cartRepository.save(cart);
    }
}
