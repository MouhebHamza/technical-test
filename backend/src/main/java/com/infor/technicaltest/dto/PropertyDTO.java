package com.infor.technicaltest.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PropertyDTO {
    private Long id;
    private String buildingName;
    private String propertyType;
    private String city;
    private String address;

    private String country;
    private Double price;
    private String availability;

}
