package com.infor.technicaltest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class ReservationDTO {
    @Schema(hidden = true)
    private Long id;
    private Long propertyId;
    @Schema(hidden = true)
    private Double moneySpent;
    private LocalDateTime fromDate;
    private LocalDateTime toDate;

}