package com.infor.technicaltest.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "reservations")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "property_id", nullable = false)
    private Property property;

    @NotNull(message = "Money spent cannot be null")
    @Min(value = 1, message = "Money spent must be at least 1")
    private Double moneySpent;

    @NotNull(message = "From date cannot be null")
    @Future(message = "From date must be in the future")
    private LocalDateTime fromDate;

    @NotNull(message = "To date cannot be null")
    @Future(message = "To date must be in the future")
    private LocalDateTime toDate;

    @AssertTrue(message = "To date must be after from date")
    public boolean isToDateAfterFromDate() {
        if (fromDate == null || toDate == null) {
            return true;
        }
        return toDate.isAfter(fromDate);
    }
}
