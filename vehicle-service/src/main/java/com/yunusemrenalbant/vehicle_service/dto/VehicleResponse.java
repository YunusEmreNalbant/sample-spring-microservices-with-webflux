package com.yunusemrenalbant.vehicle_service.dto;

public record VehicleResponse(
        Long id,
        String brand,
        String model,
        String color
) { }
