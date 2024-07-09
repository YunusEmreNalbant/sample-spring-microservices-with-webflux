package com.yunusemrenalbant.rent_service.dto;

import com.yunusemrenalbant.rent_service.domain.UserDataRead;
import com.yunusemrenalbant.rent_service.domain.VehicleDataRead;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record RentResponse(
        UserDataRead user,
        VehicleDataRead vehicle,
        LocalDate startDate,
        LocalDate endDate
) {
}
