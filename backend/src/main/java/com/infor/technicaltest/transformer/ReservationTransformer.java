package com.infor.technicaltest.transformer;

import com.infor.technicaltest.dto.ReservationDTO;
import com.infor.technicaltest.entity.Property;
import com.infor.technicaltest.entity.Reservation;
import org.springframework.stereotype.Component;

@Component
public class ReservationTransformer {

    public ReservationDTO toDTO(Reservation reservation) {
        ReservationDTO reservationDTO = new ReservationDTO();
        reservationDTO.setId(reservation.getId());
        reservationDTO.setPropertyId(reservation.getProperty().getId());  // Mapping property ID
        reservationDTO.setMoneySpent(reservation.getMoneySpent());
        reservationDTO.setFromDate(reservation.getFromDate());
        reservationDTO.setToDate(reservation.getToDate());
        return reservationDTO;
    }

    public Reservation toEntity(ReservationDTO reservationDTO) {
        Reservation reservation = new Reservation();
        reservation.setId(reservationDTO.getId());
        Property property = new Property();
        property.setId(reservationDTO.getPropertyId());
        reservation.setProperty(property);
        reservation.setMoneySpent(reservationDTO.getMoneySpent());
        reservation.setFromDate(reservationDTO.getFromDate());
        reservation.setToDate(reservationDTO.getToDate());
        return reservation;
    }
}
