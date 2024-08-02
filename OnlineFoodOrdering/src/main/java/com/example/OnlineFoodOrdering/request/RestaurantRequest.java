package com.example.OnlineFoodOrdering.request;

import com.example.OnlineFoodOrdering.model.Address;
import com.example.OnlineFoodOrdering.model.ContactInformation;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class RestaurantRequest {
    private Long id;
    private String name;
    private String description;
    private String cuisineType;
    private Address address;
    private ContactInformation contactInformation;
    private String openingHours;
//    private String closingHours;
    private List<String> images;
//    private LocalDateTime createdAt;
}
