package com.yunusemrenalbant.vehicle_service.service;

import com.yunusemrenalbant.vehicle_service.dto.VehicleRequest;
import com.yunusemrenalbant.vehicle_service.dto.VehicleResponse;
import com.yunusemrenalbant.vehicle_service.model.Vehicle;
import com.yunusemrenalbant.vehicle_service.repository.VehicleRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VehicleService {
    private final VehicleRepository vehicleRepository;

    public VehicleService(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    public List<VehicleResponse> getAllVehicles() {
        List<Vehicle> vehicles = vehicleRepository.findAll();

        return vehicles.stream()
                .map(this::mapToVehicleResponse)
                .collect(Collectors.toList());
    }

    public VehicleResponse getVehicleById(Long id) {
        return vehicleRepository.findById(id)
                .map(this::mapToVehicleResponse)
                .orElseThrow(() -> new IllegalArgumentException("Araç Bulunamadı: " + id));
    }

    public VehicleResponse create(VehicleRequest vehicleRequest) {
        Vehicle vehicle = new Vehicle(vehicleRequest.brand(), vehicleRequest.model(), vehicleRequest.color());

        Vehicle createdVehicle = vehicleRepository.save(vehicle);

        return mapToVehicleResponse(createdVehicle);
    }

    public VehicleResponse update(Long id, VehicleRequest vehicleRequest) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Araç bulunamadı: " + id));

        vehicle.setBrand(vehicleRequest.brand());
        vehicle.setModel(vehicleRequest.model());
        vehicle.setColor(vehicleRequest.color());

        Vehicle updatedVehicle = vehicleRepository.save(vehicle);

        return mapToVehicleResponse(updatedVehicle);
    }

    public void delete(Long id) {
        Vehicle vehicle = vehicleRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Araç bulunamadı: " + id));

        vehicleRepository.deleteById(vehicle.getId());
    }



    private VehicleResponse mapToVehicleResponse(Vehicle vehicle) {
        return new VehicleResponse(
                vehicle.getId(),
                vehicle.getBrand(),
                vehicle.getModel(),
                vehicle.getColor()
        );
    }


}
