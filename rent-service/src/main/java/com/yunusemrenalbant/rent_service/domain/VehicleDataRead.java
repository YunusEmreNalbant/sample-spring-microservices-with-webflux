package com.yunusemrenalbant.rent_service.domain;

import lombok.Builder;

@Builder
public record VehicleDataRead(
        Long id,
        String brand,
        String model,
        String color
) {
}
