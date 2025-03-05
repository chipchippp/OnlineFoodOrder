package com.example.OnlineFoodOrdering.model;

import jakarta.persistence.*;

@Entity
@Table(name = "address")
public class Address extends AbstractEntity {
    @Column(name = "apartment_number")
    private String apartmentNumber;
    @Column(name = "floor")
    private String floor;
    @Column(name = "building")
    private String building;

    @Column(name = "street_number")
    private String streetNumber;
    @Column(name = "street")
    private String street;
    @Column(name = "city")
    private String city;
    @Column(name = "country")
    private String country;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Column(name = "address_type")
    private Integer addressType;
}
