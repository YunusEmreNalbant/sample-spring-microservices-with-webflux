package com.yunusemrenalbant.vehicle_service.dto;

public record VehicleRequest(
        String brand,
        String model,
        String color
) { }
