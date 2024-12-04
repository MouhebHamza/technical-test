package com.infor.technicaltest.controller;

import com.infor.technicaltest.dto.PropertyDTO;
import com.infor.technicaltest.dto.ReservationDTO;
import com.infor.technicaltest.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;
    @GetMapping
    public ResponseEntity<List<ReservationDTO>> getAllReservations() {
        List<ReservationDTO> reservations = reservationService.findAllReservations();
        return ResponseEntity.ok(reservations);
    }
    // Create a reservation and return the ReservationDTO
    @PostMapping
    public ResponseEntity<ReservationDTO> createReservation(@RequestBody ReservationDTO reservationDTO) {
        ReservationDTO createdReservation = reservationService.createReservation(reservationDTO);
        return ResponseEntity.status(201).body(createdReservation); // HTTP 201 Created
    }

    // Get reservations by property ID and return a list of ReservationDTOs
    @GetMapping("/property/{propertyId}")
    public ResponseEntity<List<ReservationDTO>> getReservationsByProperty(@PathVariable Long propertyId) {
        List<ReservationDTO> reservations = reservationService.findReservationsByProperty(propertyId);
        return ResponseEntity.ok(reservations);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationDTO> getReservationById(@PathVariable Long id) {
        ReservationDTO reservation = reservationService.findReservationById(id);
        return ResponseEntity.ok(reservation);
    }



    // Get reservations by date range and return a list of ReservationDTOs
    @GetMapping("/dates")
    public ResponseEntity<List<ReservationDTO>> getReservationsByDateRange(
            @RequestParam("start") String start,
            @RequestParam("end") String end) {
        LocalDateTime startDate = LocalDateTime.parse(start);
        LocalDateTime endDate = LocalDateTime.parse(end);
        List<ReservationDTO> reservations = reservationService.findReservationsByDateRange(startDate, endDate);
        return ResponseEntity.ok(reservations);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ReservationDTO>> filterReservations(
            @RequestParam(required = false) String propertyType,
            @RequestParam(required = false) String buildingName,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String address,
            @RequestParam(required = false) String country,
            @RequestParam(required = false) String fromDate,
            @RequestParam(required = false) String toDate,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice) {

        LocalDateTime parsedFromDate = fromDate != null ? LocalDateTime.parse(fromDate) : null;
        LocalDateTime parsedToDate = toDate != null ? LocalDateTime.parse(toDate) : null;

        List<ReservationDTO> reservations = reservationService.filterReservations(
                propertyType, buildingName, city, address, country,
                parsedFromDate, parsedToDate, minPrice, maxPrice);

        return ResponseEntity.ok(reservations);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body("Bad Request: " + ex.getMessage());
    }

    // Handle IllegalStateException
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<String> handleIllegalStateException(IllegalStateException ex) {
        return ResponseEntity.status(409).body("Conflict: " + ex.getMessage());
    }

    // Handle any generic exception
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        return ResponseEntity.status(500).body("Internal Server Error: " + ex.getMessage());
    }
}
