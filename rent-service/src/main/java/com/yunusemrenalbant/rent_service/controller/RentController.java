package com.yunusemrenalbant.rent_service.controller;

import com.yunusemrenalbant.rent_service.dto.RentRequest;
import com.yunusemrenalbant.rent_service.dto.RentResponse;
import com.yunusemrenalbant.rent_service.model.Rent;
import com.yunusemrenalbant.rent_service.service.RentService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/rents")
public class RentController {

    private final RentService rentService;

    public RentController(RentService rentService) {
        this.rentService = rentService;
    }

    @PostMapping
    public Mono<Rent> create(@RequestBody RentRequest rentRequest) {
        return rentService.create(rentRequest);
    }

    @GetMapping
    public Flux<RentResponse> listAllRents() {
        return rentService.listAllRents();
    }
}
