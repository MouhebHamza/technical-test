package com.infor.technicaltest.repository;

import com.infor.technicaltest.entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PropertyRepository extends JpaRepository<Property, Long> {
    List<Property> findByCityContaining(String city);
    List<Property> findByCountry(String country);
    List<Property> findByPriceBetween(Double minPrice, Double maxPrice);
    List<Property> findByBuildingNameContaining(String name);
}
