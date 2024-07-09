package com.yunusemrenalbant.vehicle_service.controller;

import com.yunusemrenalbant.vehicle_service.dto.VehicleRequest;
import com.yunusemrenalbant.vehicle_service.dto.VehicleResponse;
import com.yunusemrenalbant.vehicle_service.service.VehicleService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {

    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<VehicleResponse> getAllUsers() {
        return vehicleService.getAllVehicles();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public VehicleResponse getVehicleById(@PathVariable Long id) {
        return vehicleService.getVehicleById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VehicleResponse create(@RequestBody VehicleRequest vehicleRequest) {
        return vehicleService.create(vehicleRequest);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public VehicleResponse update(@PathVariable Long id, @RequestBody VehicleRequest vehicleRequest) {
        return vehicleService.update(id, vehicleRequest);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        vehicleService.delete(id);
    }
}
