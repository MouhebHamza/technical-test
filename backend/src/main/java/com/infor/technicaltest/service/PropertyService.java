package com.infor.technicaltest.service;

import com.infor.technicaltest.dto.PropertyDTO;
import com.infor.technicaltest.entity.Property;
import com.infor.technicaltest.repository.PropertyRepository;
import com.infor.technicaltest.transformer.PropertyTransformer;
import jakarta.el.PropertyNotFoundException;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class PropertyService {

    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private PropertyTransformer propertyTransformer;

    @PersistenceContext
    private EntityManager entityManager;

    public List<PropertyDTO> findAllProperties() {
        List<Property> properties = propertyRepository.findAll();
        List<PropertyDTO> propertyDTOs = new ArrayList<>();
        for (Property property : properties) {
            propertyDTOs.add(propertyTransformer.toDTO(property));
        }
        return propertyDTOs;
    }
    public PropertyDTO getPropertyById(Long id) {
        Optional<Property> property = propertyRepository.findById(id);
        if (property.isPresent()) {
            return propertyTransformer.toDTO(property.get());  // Convert Property entity to PropertyDTO
        }
        return null;  // If property is not found, return null
    }
    // Create a new property
    public PropertyDTO createProperty(PropertyDTO propertyDTO) {
        Property property = propertyTransformer.toEntity(propertyDTO);
        Property savedProperty = propertyRepository.save(property);
        return propertyTransformer.toDTO(savedProperty);
    }


    // Delete a property by ID
    public void deleteProperty(Long id) {
        if (!propertyRepository.existsById(id)) {
            throw new RuntimeException("Property not found");
        }
        propertyRepository.deleteById(id);
    }


    public List<PropertyDTO> searchProperties(String city, String buildingName, String address, String country, Double minPrice, Double maxPrice, String availability) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Property> query = cb.createQuery(Property.class);
        Root<Property> root = query.from(Property.class);

        List<Predicate> predicates = new ArrayList<>();

        if (city != null && !city.isEmpty()) {
            predicates.add(cb.like(root.get("city"), "%" + city + "%"));
        }
        if (country != null && !country.isEmpty()) {
            predicates.add(cb.like(root.get("country"), "%" + country + "%"));
        }

        if (buildingName != null && !buildingName.isEmpty()) {
            predicates.add(cb.like(root.get("buildingName"), "%" + buildingName + "%"));
        }

        if (address != null && !address.isEmpty()) {
            predicates.add(cb.like(root.get("address"), "%" + address + "%"));
        }

        if (minPrice != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("price"), minPrice));
        }
        if (maxPrice != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get("price"), maxPrice));
        }

        if (availability != null && !availability.isEmpty()) {
            predicates.add(cb.like(root.get("availability"), "%" + availability + "%"));
        }
        query.where(cb.and(predicates.toArray(new Predicate[0])));
        List<Property> properties = entityManager.createQuery(query).getResultList();

        List<PropertyDTO> propertyDTOs = new ArrayList<>();
        for (Property property : properties) {
            propertyDTOs.add(propertyTransformer.toDTO(property));
        }
        return propertyDTOs;
    }
}
