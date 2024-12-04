package com.infor.technicaltest.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Getter
@Setter
@Table(name = "properties")
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Building name cannot be null")
    @Size(min = 1, max = 100, message = "Building name must be between 1 and 100 characters")
    private String buildingName;

    @NotNull(message = "Property type cannot be null")
    private String propertyType;

    @NotNull(message = "City cannot be null")
    private String city;

    @NotNull(message = "Country cannot be null")
    @Size(min = 3, max = 3, message = "Country code must be 3 characters")
    private String country;

    @NotNull(message = "Address cannot be null")
    private String address;

    @NotNull(message = "Price cannot be null")
    @Min(value = 1, message = "Price must be greater than 0")
    private Double price;

    @NotNull(message = "Availability cannot be null")
    private String availability;
}