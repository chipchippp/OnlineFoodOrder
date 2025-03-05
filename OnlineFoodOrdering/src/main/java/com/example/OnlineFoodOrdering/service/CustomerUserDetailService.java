package com.example.OnlineFoodOrdering.service;

import com.example.OnlineFoodOrdering.model.UserEntity;
import com.example.OnlineFoodOrdering.repository.UserRepository;
import com.example.OnlineFoodOrdering.util.ERole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class CustomerUserDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    public CustomerUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findUserByEmail(email);
        if (userEntity == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }

        // Gán role mặc định nếu null
        ERole role = (userEntity.getRole() != null) ? userEntity.getRole() : ERole.ROLE_CUSTOMER;

        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(role.name()));

        return new User(userEntity.getEmail(), userEntity.getPassword(), authorities);
    }
}
