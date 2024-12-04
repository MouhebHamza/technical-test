package com.infor.technicaltest.transformer;

import com.infor.technicaltest.dto.PropertyDTO;
import com.infor.technicaltest.entity.Property;
import org.springframework.stereotype.Component;

@Component
public class PropertyTransformer {

    public PropertyDTO toDTO(Property property) {
        PropertyDTO propertyDTO = new PropertyDTO();
        propertyDTO.setId(property.getId());
        propertyDTO.setBuildingName(property.getBuildingName());
        propertyDTO.setPropertyType(property.getPropertyType());
        propertyDTO.setCity(property.getCity());
        propertyDTO.setCountry(property.getCountry());
        propertyDTO.setAddress(property.getAddress());
        propertyDTO.setPrice(property.getPrice());
        propertyDTO.setAvailability(property.getAvailability());
        return propertyDTO;
    }

    public Property toEntity(PropertyDTO propertyDTO) {
        Property property = new Property();
        property.setId(propertyDTO.getId());
        property.setBuildingName(propertyDTO.getBuildingName());
        property.setPropertyType(propertyDTO.getPropertyType());
        property.setCity(propertyDTO.getCity());
        property.setCountry(propertyDTO.getCountry());
        property.setAddress(propertyDTO.getAddress());
        property.setPrice(propertyDTO.getPrice());
        property.setAvailability(propertyDTO.getAvailability());
        return property;
    }
}
