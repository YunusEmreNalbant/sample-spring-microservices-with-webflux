package com.yunusemrenalbant.rent_service.service;

import com.yunusemrenalbant.rent_service.config.internal.UserServiceClient;
import com.yunusemrenalbant.rent_service.config.internal.VehicleServiceClient;
import com.yunusemrenalbant.rent_service.domain.UserDataRead;
import com.yunusemrenalbant.rent_service.domain.VehicleDataRead;
import com.yunusemrenalbant.rent_service.dto.RentRequest;
import com.yunusemrenalbant.rent_service.dto.RentResponse;
import com.yunusemrenalbant.rent_service.exception.GenericException;
import com.yunusemrenalbant.rent_service.model.Rent;
import com.yunusemrenalbant.rent_service.repository.RentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

@Service
@RequiredArgsConstructor
public class RentService {

    private final RentRepository rentRepository;
    private final UserServiceClient userServiceClient;
    private final VehicleServiceClient vehicleServiceClient;


    public Mono<Rent> create(RentRequest rentRequest) {
        Mono<VehicleDataRead> vehicleMono = vehicleServiceClient
                .getVehicleDataWithId(rentRequest.vehicleId());

        Mono<UserDataRead> userMono = userServiceClient.getUserDataWithId(rentRequest.userId());

        Mono<Tuple2<UserDataRead, VehicleDataRead>> tuple2Mono = Mono.zip(userMono, vehicleMono);
       // Mono<Tuple2<UserDataRead, VehicleDataRead>> tuple2Mono = userMono.zipWith(vehicleMono);

        return tuple2Mono
                .onErrorResume(err -> Mono.error(new GenericException("veri çekme sırasında bir hata oluştu")))
                .flatMap(t -> createWithUserAndVehicle(rentRequest))
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Geçersiz bilgiler geldi!")));
    }

    public Flux<RentResponse> listAllRents() {
        return Flux.fromIterable(rentRepository.findAll())
                .flatMap(this::mapRentToRentResponse);
    }

    private Mono<RentResponse> mapRentToRentResponse(Rent rent) {
        Mono<UserDataRead> userMono = userServiceClient.getUserDataWithId(rent.getUserId());
        Mono<VehicleDataRead> vehicleMono = vehicleServiceClient.getVehicleDataWithId(rent.getVehicleId());

        return Mono.zip(userMono, vehicleMono)
                .map(tuple -> RentResponse.builder()
                        .user(tuple.getT1())
                        .vehicle(tuple.getT2())
                        .startDate(rent.getStartDate())
                        .endDate(rent.getEndDate())
                        .build());
    }

    private Mono<Rent> createWithUserAndVehicle(RentRequest rentRequest) {
            return Mono.fromCallable(()->rentRepository.save(Rent.builder()
                    .startDate(rentRequest.startDate())
                    .endDate(rentRequest.endDate())
                    .vehicleId(rentRequest.vehicleId())
                    .userId(rentRequest.userId())
                    .build()));
    }
}
