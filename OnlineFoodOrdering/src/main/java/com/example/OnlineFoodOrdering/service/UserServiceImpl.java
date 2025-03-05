package com.example.OnlineFoodOrdering.service;

import com.example.OnlineFoodOrdering.config.JwtProvider;
import com.example.OnlineFoodOrdering.dto.request.AddressDTO;
import com.example.OnlineFoodOrdering.dto.request.UserRequestDTO;
import com.example.OnlineFoodOrdering.exception.ResourceNotFoundException;
import com.example.OnlineFoodOrdering.model.Address;
import com.example.OnlineFoodOrdering.model.UserEntity;
import com.example.OnlineFoodOrdering.repository.UserRepository;
import com.example.OnlineFoodOrdering.service.impl.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, JwtProvider jwtProvider) {
        this.userRepository = userRepository;
        this.jwtProvider = jwtProvider;
    }

    @Override
    public UserEntity findByUserByJwtToken(String jwt) throws Exception {
        String email = jwtProvider.getEmailFromJwtToken(jwt);
        UserEntity userEntity = userRepository.findUserByEmail(email);
        if (userEntity == null) {
            throw new Exception("User not found with email: " + email);
        }

        return userEntity;
    }

    @Override
    public UserEntity findUserByEmail(String email) throws Exception {
        UserEntity userEntity = userRepository.findUserByEmail(email);

        if (userEntity == null) {
            throw new Exception("User not found with email: " + email);
        }
        return userEntity;
    }

    @Override
    public long addUser(UserRequestDTO user) {
        UserEntity userEntity = UserEntity.builder()
                .fullName(user.getFullName())
                .username(user.getUsername())
                .phone(user.getPhone())
                .email(user.getEmail())
                .password(user.getPassword())
                .dateOfBirth(user.getDateOfBirth())
                .gender(user.getGender())
                .status(user.getStatus())
                .addresses(convertToAddress(user.getAddresses()))
                .build();
        user.getAddresses().forEach(a -> userEntity.saveAddress(Address.builder()
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
    public void updateUser(long userId, UserRequestDTO user) {

    }

    @Override
    public void deleteUser(long userId) {

    }

    private UserEntity getUserById(long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }
}
