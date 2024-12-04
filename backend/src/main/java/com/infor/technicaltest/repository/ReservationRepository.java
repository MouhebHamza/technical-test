package com.infor.technicaltest.repository;

import com.infor.technicaltest.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByProperty_Id(Long propertyId);

    List<Reservation> findByFromDateBetween(LocalDateTime startDate, LocalDateTime endDate);
}
