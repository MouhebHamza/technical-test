package com.infor.technicaltest.service;

import com.infor.technicaltest.dto.ReservationDTO;
import com.infor.technicaltest.entity.Property;
import com.infor.technicaltest.entity.Reservation;
import com.infor.technicaltest.repository.PropertyRepository;
import com.infor.technicaltest.repository.ReservationRepository;
import com.infor.technicaltest.transformer.ReservationTransformer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private ReservationTransformer reservationTransformer;
    @PersistenceContext
    private EntityManager entityManager;
    public List<ReservationDTO> findAllReservations() {
        List<Reservation> reservations = reservationRepository.findAll();
        List<ReservationDTO> reservationDTOs = new ArrayList<>();
        for (Reservation reservation : reservations) {
            reservationDTOs.add(reservationTransformer.toDTO(reservation));
        }
        return reservationDTOs;
    }
    public ReservationDTO createReservation(ReservationDTO reservationDTO) {
        Reservation reservation = reservationTransformer.toEntity(reservationDTO);

              if (reservation.getFromDate() != null && reservation.getToDate() != null &&
                !reservation.getFromDate().isBefore(reservation.getToDate())) {
            throw new IllegalArgumentException("From date must be earlier than to date.");
        }

        if (reservation.getProperty() == null) {
            throw new IllegalArgumentException("Property not found");
        }

        Optional<Property> propertyOptional = propertyRepository.findById(reservation.getProperty().getId());
        if (!propertyOptional.isPresent()) {
            throw new IllegalArgumentException("Property not found");
        }

        Property property = propertyOptional.get();

        if ("Booked".equalsIgnoreCase(property.getAvailability())) {
            throw new IllegalStateException("Property is not available");
        }

        double discount = property.getPropertyType().equals("Hotel Room") ? 0.15 : 0.10;
        long days = java.time.Duration.between(reservation.getFromDate(), reservation.getToDate()).toDays();
        double totalCost = property.getPrice() * days * (1 - discount);

        if (days > 10) {
            totalCost *= 1.05;
        }

        reservation.setMoneySpent(totalCost);
        reservation.setProperty(property);

        Reservation savedReservation = reservationRepository.save(reservation);
        property.setAvailability("Booked");
        propertyRepository.save(property);

        return reservationTransformer.toDTO(savedReservation);
    }



    // Find a reservation by ID
    public ReservationDTO findReservationById(Long id) {
        Optional<Reservation> reservationOptional = reservationRepository.findById(id);
        if (reservationOptional.isPresent()) {
            return reservationTransformer.toDTO(reservationOptional.get());
        }
        throw new RuntimeException("Reservation not found");
    }



    public void cancelReservation(Long id) {
        Optional<Reservation> reservationOptional = reservationRepository.findById(id);
        if (reservationOptional.isPresent()) {
            reservationRepository.deleteById(id);
        } else {
            throw new RuntimeException("Reservation not found");
        }
    }
    public List<ReservationDTO> findReservationsByProperty(Long propertyId) {
        List<Reservation> reservations = reservationRepository.findByProperty_Id(propertyId);
        return reservations.stream()
                .map(reservationTransformer::toDTO)
                .collect(Collectors.toList());
    }

    public List<ReservationDTO> findReservationsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        List<Reservation> reservations = reservationRepository.findByFromDateBetween(startDate, endDate);
        return reservations.stream()
                .map(reservationTransformer::toDTO)
                .collect(Collectors.toList());
    }

    public List<ReservationDTO> filterReservations(String propertyType, String buildingName, String city, String address,
                                                   String country, LocalDateTime fromDate, LocalDateTime toDate, Double minPrice, Double maxPrice) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Reservation> query = cb.createQuery(Reservation.class);
        Root<Reservation> root = query.from(Reservation.class);

        Join<Object, Object> propertyJoin = root.join("property");

        List<Predicate> predicates = new ArrayList<>();

        if (propertyType != null && !propertyType.isEmpty()) {
            predicates.add(cb.equal(propertyJoin.get("propertyType"), propertyType));
        }
        if (buildingName != null && !buildingName.isEmpty()) {
            predicates.add(cb.like(propertyJoin.get("buildingName"), "%" + buildingName + "%"));
        }
        if (city != null && !city.isEmpty()) {
            predicates.add(cb.like(propertyJoin.get("city"), "%" + city + "%"));
        }
        if (address != null && !address.isEmpty()) {
            predicates.add(cb.like(propertyJoin.get("address"), "%" + address + "%"));
        }
        if (country != null && !country.isEmpty()) {
            predicates.add(cb.like(propertyJoin.get("country"), "%" + country + "%"));
        }
        if (fromDate != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("fromDate"), fromDate));
        }
        if (toDate != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get("toDate"), toDate));
        }
        if (minPrice != null) {
            predicates.add(cb.greaterThanOrEqualTo(propertyJoin.get("price"), minPrice));
        }
        if (maxPrice != null) {
            predicates.add(cb.lessThanOrEqualTo(propertyJoin.get("price"), maxPrice));
        }

        query.where(cb.and(predicates.toArray(new Predicate[0])));

        List<Reservation> reservations = entityManager.createQuery(query).getResultList();
        return reservations.stream()
                .map(reservationTransformer::toDTO)
                .collect(Collectors.toList());
    }
}