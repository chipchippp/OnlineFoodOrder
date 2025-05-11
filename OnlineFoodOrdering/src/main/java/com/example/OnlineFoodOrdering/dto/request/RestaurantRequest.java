package com.example.OnlineFoodOrdering.dto.request;

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
    private Long addressId;
    private ContactInformation contactInformation;
    private String openingHours;
//    private String closingHours;
    private List<String> images;
//    private LocalDateTime createdAt;
}
