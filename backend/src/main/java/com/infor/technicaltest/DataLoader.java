//package com.infor.technicaltest;
//
//import com.infor.technicaltest.entity.Property;
//import com.infor.technicaltest.repository.PropertyRepository;
//import jakarta.annotation.PostConstruct;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.util.Arrays;
//
//@Component
//public class DataLoader {
//
//    @Autowired
//    private PropertyRepository propertyRepository;
//
//    @PostConstruct
//    public void loadProperties() {
//        // Create a list of sample properties
//        Property property1 = new Property();
//        property1.setBuildingName("Sky Tower");
//        property1.setPropertyType("Apartment");
//        property1.setCity("New York");
//        property1.setCountry("USA");
//        property1.setAddress("123 Main St");
//        property1.setPrice(2500.00);
//        property1.setAvailability("Available");
//
//        Property property2 = new Property();
//        property2.setBuildingName("Ocean View Villas");
//        property2.setPropertyType("Villa");
//        property2.setCity("Miami");
//        property2.setCountry("USA");
//        property2.setAddress("456 Beach Rd");
//        property2.setPrice(5500.00);
//        property2.setAvailability("Booked");
//
//        Property property3 = new Property();
//        property3.setBuildingName("Mountain Retreat");
//        property3.setPropertyType("Cabin");
//        property3.setCity("Aspen");
//        property3.setCountry("USA");
//        property3.setAddress("789 Snowy Rd");
//        property3.setPrice(3500.00);
//        property3.setAvailability("Available");
//
//        // Save the properties to the database
//        propertyRepository.saveAll(Arrays.asList(property1, property2, property3));
//
//        System.out.println("Sample properties loaded successfully!");
//    }
//}
