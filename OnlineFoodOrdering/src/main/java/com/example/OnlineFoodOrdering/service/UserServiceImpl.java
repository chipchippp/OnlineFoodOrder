package com.example.OnlineFoodOrdering.service;

import com.example.OnlineFoodOrdering.config.JwtProvider;
import com.example.OnlineFoodOrdering.dto.request.AddressDTO;
import com.example.OnlineFoodOrdering.dto.request.UserRequestDTO;
import com.example.OnlineFoodOrdering.dto.response.UserDetailResponse;
import com.example.OnlineFoodOrdering.exception.ResourceNotFoundException;
import com.example.OnlineFoodOrdering.model.Address;
import com.example.OnlineFoodOrdering.model.UserEntity;
import com.example.OnlineFoodOrdering.repository.UserRepository;
import com.example.OnlineFoodOrdering.service.impl.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@Slf4j
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetailsService getUserDetailsService() {
        return username -> userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("user.not.found"));
    }

    @Override
    public UserEntity getByUsername(String username) {
        return userRepository.findUserByUsername(username).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @Override
    public UserEntity getUserByEmail(String email) {
        return userRepository.findUserByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @Override
    public long saveUser(UserEntity user) {
        userRepository.save(user);
        return user.getId();
    }

    @Override
    public UserDetailResponse getUserId(long userId) {
        UserEntity userEntity = getUserById(userId);
        return UserDetailResponse.builder()
                .id(userEntity.getId())
                .fullName(userEntity.getFullName())
                .username(userEntity.getUsername())
                .email(userEntity.getEmail())
                .phone(userEntity.getPhone())
                .build();
    }

    @Override
    public UserEntity findByUserByJwtToken(String jwt) throws Exception {
        String email = jwtProvider.getEmailFromJwtToken(jwt);
        Optional<UserEntity> userEntity = userRepository.findUserByEmail(email);
        if (userEntity == null) {
            throw new Exception("User not found with email: " + email);
        }

        return userEntity.get();
    }

    @Override
    public UserEntity findUserByEmail(String email) throws Exception {
        Optional<UserEntity> userEntity = userRepository.findUserByEmail(email);

        if (userEntity == null) {
            throw new Exception("User not found with email: " + email);
        }
        return userEntity.get();
    }

    @Override
    public long addUser(UserRequestDTO request) {
        UserEntity userEntity = UserEntity.builder()
                .fullName(request.getFullName())
                .username(request.getUsername())
                .phone(request.getPhone())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .dateOfBirth(request.getDateOfBirth())
                .gender(request.getGender())
                .status(request.getStatus())
                .addresses(convertToAddress(request.getAddresses()))
                .build();
        request.getAddresses().forEach(a -> userEntity.saveAddress(Address.builder()
                .apartmentNumber(a.getApartmentNumber())
                .floor(a.getFloor())
                .building(a.getBuilding())
                .streetNumber(a.getStreetNumber())
                .street(a.getStreet())
                .city(a.getCity())
                .country(a.getCountry())
                .addressType(a.getAddressType())
                .build())
        );
        userRepository.save(userEntity);

        log.info("User {} added successfully", userEntity.getId());
        return userEntity.getId();
    }

    private Set<Address> convertToAddress(Set<AddressDTO> addresses) {
        Set<Address> result = new HashSet<>();
        addresses.forEach(a ->
                result.add(Address.builder()
                        .apartmentNumber(a.getApartmentNumber())
                        .floor(a.getFloor())
                        .building(a.getBuilding())
                        .streetNumber(a.getStreetNumber())
                        .street(a.getStreet())
                        .city(a.getCity())
                        .country(a.getCountry())
                        .addressType(a.getAddressType())
                        .build())
        );
        return result;
    }

    @Override
    public void updateUser(long userId, UserRequestDTO request) {
        UserEntity userEntity = getUserById(userId);
        userEntity.setFullName(request.getFullName());
        userEntity.setUsername(request.getUsername());
        if (!request.getEmail().equals(userEntity.getEmail())) {
            userEntity.setEmail(request.getEmail());
        }
        userEntity.setPhone(request.getPhone());
        userEntity.setPassword(passwordEncoder.encode(request.getPassword()));
        userEntity.setDateOfBirth(request.getDateOfBirth());
        userEntity.setGender(request.getGender());
        userEntity.setStatus(request.getStatus());
        userEntity.setAddresses(convertToAddress(request.getAddresses()));

        userRepository.save(userEntity);
        log.info("User {} updated successfully", userEntity.getId());
    }

    @Override
    public void deleteUser(long userId) {
        userRepository.deleteById(userId);
        log.info("User {} deleted successfully", userId);
    }

    private UserEntity getUserById(long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }
}
