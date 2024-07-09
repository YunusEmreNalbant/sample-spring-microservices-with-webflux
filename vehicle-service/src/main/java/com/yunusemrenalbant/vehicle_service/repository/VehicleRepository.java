package com.yunusemrenalbant.vehicle_service.repository;

import com.yunusemrenalbant.vehicle_service.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
}
