package com.example.OnlineFoodOrdering.service;

import com.example.OnlineFoodOrdering.dto.request.RestaurantDto;
import com.example.OnlineFoodOrdering.exception.ResourceNotFoundException;
import com.example.OnlineFoodOrdering.model.Address;
import com.example.OnlineFoodOrdering.model.Restaurant;
import com.example.OnlineFoodOrdering.model.UserEntity;
import com.example.OnlineFoodOrdering.repository.AddressRepository;
import com.example.OnlineFoodOrdering.repository.RestaurantRepository;
import com.example.OnlineFoodOrdering.repository.UserRepository;
import com.example.OnlineFoodOrdering.dto.request.RestaurantRequest;
import com.example.OnlineFoodOrdering.service.impl.RestaurantService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class RestaurantServiceImpl implements RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    @Autowired
    public RestaurantServiceImpl(RestaurantRepository restaurantRepository, AddressRepository addressRepository, UserRepository userRepository) {
        this.restaurantRepository = restaurantRepository;
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Restaurant saveRestaurant(RestaurantRequest res, UserEntity userEntity) throws Exception {
        log.info("Saving restaurant with name: {}", res.getName());

        Address address = addressRepository.save(res.getAddress());
        Restaurant restaurant = new Restaurant();
        restaurant.setAddress(address);
        restaurant.setContactInformation(res.getContactInformation());
        restaurant.setCuisineType(res.getCuisineType());
        restaurant.setDescription(res.getDescription());
        restaurant.setImages(res.getImages());
        restaurant.setName(res.getName());
        restaurant.setOpeningHours(res.getOpeningHours());
        restaurant.setRegistrationDate(LocalDateTime.now());
        restaurant.setOwner(userEntity);
        if (restaurant == null) {
            throw new Exception("Restaurant entity must not be null");
        }
        return restaurantRepository.save(restaurant);
    }

    @Override
    public Restaurant updateRestaurant(Long restaurantId, RestaurantRequest updateRes) throws Exception {
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(() -> new Exception("updateRestaurant not found"));
        if (restaurant.getCuisineType()!=null) restaurant.setCuisineType(updateRes.getCuisineType());
        if (restaurant.getDescription()!=null) restaurant.setDescription(updateRes.getDescription());
        if (restaurant.getName()!=null) restaurant.setName(updateRes.getName());

        return restaurantRepository.save(restaurant);
    }

    @Override
    public void deleteRestaurant(Long id) throws Exception {
        Restaurant restaurant = restaurantRepository.findById(id).orElseThrow(() -> new Exception("deleteRestaurant not found"));
        restaurantRepository.delete(restaurant);
    }

    @Override
    public List<Restaurant> getAllRestaurants() throws Exception {
        return restaurantRepository.findAll();
    }

    @Override
    public List<Restaurant> searchRestaurant(String keyWord) throws Exception {
        return restaurantRepository.findBySearchQuery(keyWord);
    }

    @Override
    public Restaurant getRestaurantByUserId(Long id) throws Exception {
        return restaurantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("getRestaurantByUserId not found for ID: " + id));
    }

    @Override
    public Restaurant findRestaurantById(Long id) throws Exception {
        Optional<Restaurant> restaurant = restaurantRepository.findById(id);
        if(restaurant.isEmpty()){
            throw new Exception("findRestaurantById not found with id: "+id);
        }
        return restaurant.get();
    }

    @Override
    public RestaurantDto addToFavorites(Long restaurantId, UserEntity userEntity) throws Exception {
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(() -> new Exception("addToFavorites not found"));
        RestaurantDto restaurantDto = new RestaurantDto();
        restaurantDto.setId(restaurantId);
        restaurantDto.setTitle(restaurant.getName());
        restaurantDto.setImages(restaurant.getImages());
        restaurantDto.setDescription(restaurant.getDescription());

        boolean isFavorite = false;
        List<RestaurantDto> favorites = userEntity.getFavorites();
        for (RestaurantDto favorite : favorites) {
            if (favorite.getId().equals(restaurantId)) {
                isFavorite = true;
                break;
            }
        }

        if (isFavorite){
            favorites.removeIf(favorite -> favorite.getId().equals(restaurantId));
        }else {
            favorites.add(restaurantDto);
        }

        userRepository.save(userEntity);

        return restaurantDto;
    }

    @Override
    public Restaurant updateRestaurantStatus(Long id) throws Exception {
        Restaurant restaurant = restaurantRepository.findById(id).orElseThrow(() -> new Exception("updateRestaurantStatus not found"));
        restaurant.setOpen(!restaurant.isOpen());
        return restaurantRepository.save(restaurant);
    }
}
