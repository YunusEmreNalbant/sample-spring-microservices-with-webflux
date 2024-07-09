package com.yunusemrenalbant.rent_service.dto;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record RentRequest(
        Long userId,
        Long vehicleId,
        LocalDate startDate,
        LocalDate endDate
) {
}
