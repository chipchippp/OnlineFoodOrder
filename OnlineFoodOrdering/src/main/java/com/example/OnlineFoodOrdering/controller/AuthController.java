package com.example.OnlineFoodOrdering.controller;

import com.example.OnlineFoodOrdering.config.JwtProvider;
import com.example.OnlineFoodOrdering.model.Cart;
import com.example.OnlineFoodOrdering.model.UserEntity;
import com.example.OnlineFoodOrdering.repository.CartRepository;
import com.example.OnlineFoodOrdering.repository.UserRepository;
import com.example.OnlineFoodOrdering.request.LoginRequest;
import com.example.OnlineFoodOrdering.response.AuthResponse;
import com.example.OnlineFoodOrdering.service.CustomerUserDetailService;
import com.example.OnlineFoodOrdering.statics.enums.ERole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final CustomerUserDetailService customerUserDetailService;
    private final CartRepository cartRepository;

    @Autowired
    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtProvider jwtProvider, CustomerUserDetailService customerUserDetailService, CartRepository cartRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
        this.customerUserDetailService = customerUserDetailService;
        this.cartRepository = cartRepository;
    }

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody UserEntity user) throws Exception {
        UserEntity isEmailExits = userRepository.findByUsername(user.getEmail());
        if (isEmailExits != null) {
            throw new  Exception("Email already exists");
        }
        UserEntity newUser = new UserEntity();
        newUser.setUsername(user.getUsername());
        newUser.setEmail(user.getEmail());
        newUser.setRole(user.getRole());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));

        UserEntity savedUser = userRepository.save(newUser);

        Cart cart = new Cart();
        cart.setCustomer(savedUser);
        cartRepository.save(cart);

        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateToken(authentication);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setMessage("Register successfully");
        authResponse.setRole(savedUser.getRole());
        return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> loginUserHandler(@RequestBody LoginRequest loginRequest) {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        Authentication authentication = authenticate(email, password);
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String role = authorities.isEmpty() ? null : authorities.iterator().next().getAuthority();

        String jwt = jwtProvider.generateToken(authentication);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setMessage("Register successfully");
        authResponse.setRole(ERole.valueOf(role));
        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }

    private Authentication authenticate(String email, String password) {
        UserDetails userDetails = customerUserDetailService.loadUserByUsername(email);
        if (userDetails == null) {
            throw new BadCredentialsException("User not found");
        }
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Wrong password");
        }
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
