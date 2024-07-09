package com.yunusemrenalbant.rent_service.service;

import com.yunusemrenalbant.rent_service.config.internal.UserServiceClient;
import com.yunusemrenalbant.rent_service.config.internal.VehicleServiceClient;
import com.yunusemrenalbant.rent_service.domain.UserDataRead;
import com.yunusemrenalbant.rent_service.domain.VehicleDataRead;
import com.yunusemrenalbant.rent_service.dto.RentRequest;
import com.yunusemrenalbant.rent_service.exception.GenericException;
import com.yunusemrenalbant.rent_service.model.Rent;
import com.yunusemrenalbant.rent_service.repository.RentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDate;


@ExtendWith(MockitoExtension.class)
class RentServiceTest {

    @InjectMocks
    private RentService service;

    @Mock
    private  RentRepository rentRepository;
    @Mock
    private  UserServiceClient userServiceClient;
    @Mock
    private  VehicleServiceClient vehicleServiceClient;



    @Test
    public void testRent() {
        RentRequest rentRequest = RentRequest.builder()
                .vehicleId(1L)
                .userId(1L)
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusDays(1))
                .build();

        VehicleDataRead vehicleResponse = VehicleDataRead.builder().id(rentRequest.vehicleId())
                .brand("FORD")
                .color("RED")
                .model("KUGA")
                .build();
        Mockito.when(vehicleServiceClient.getVehicleDataWithId(rentRequest.vehicleId()))
                        .thenReturn(Mono.just(vehicleResponse));

        UserDataRead userResponse = UserDataRead.builder()
                .email("omermutlu29@gmail.com")
                .firstName("ÖMER")
                .lastName("MUTLU")
                .id(rentRequest.userId())
                .build();

        Mockito.when(userServiceClient.getUserDataWithId(rentRequest.userId()))
                .thenReturn(Mono.just(userResponse));

        Rent rent = Rent.builder().id(1L).userId(rentRequest.userId())
                .vehicleId(rentRequest.vehicleId())
                .startDate(rentRequest.startDate())
                .endDate(rentRequest.endDate())
                .build();

        Mockito.when(rentRepository.save(Mockito.any(Rent.class)))
                        .thenReturn(rent);

        Mono<Rent> actual = service.create(rentRequest);
        StepVerifier.create(actual)
                .expectNext(rent)
                .verifyComplete();

    }

    @Test
    public void testRentWhenVehicleDoesNotExistShouldThrowException() {
        RentRequest rentRequest = RentRequest.builder()
                .vehicleId(1L)
                .userId(1L)
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusDays(1))
                .build();


        Mockito.when(vehicleServiceClient.getVehicleDataWithId(rentRequest.vehicleId()))
                .thenReturn(Mono.empty());

        UserDataRead userResponse = UserDataRead.builder()
                .email("omermutlu29@gmail.com")
                .firstName("ÖMER")
                .lastName("MUTLU")
                .id(rentRequest.userId())
                .build();

        Mockito.when(userServiceClient.getUserDataWithId(rentRequest.userId()))
                .thenReturn(Mono.just(userResponse));

        Mono<Rent> actual = service.create(rentRequest);
        StepVerifier.create(actual)
                .expectErrorMatches(err->err.getMessage().equals("Geçersiz bilgiler geldi!"))
                .verify();

    }

    @Test
    public void testRentWhenVehicleClientThrowExceptionShouldThrowException() {
        RentRequest rentRequest = RentRequest.builder()
                .vehicleId(1L)
                .userId(1L)
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusDays(1))
                .build();


        Mockito.when(vehicleServiceClient.getVehicleDataWithId(rentRequest.vehicleId()))
                .thenReturn(Mono.error(new IllegalArgumentException("test error")));

        UserDataRead userResponse = UserDataRead.builder()
                .email("omermutlu29@gmail.com")
                .firstName("ÖMER")
                .lastName("MUTLU")
                .id(rentRequest.userId())
                .build();

        Mockito.when(userServiceClient.getUserDataWithId(rentRequest.userId()))
                .thenReturn(Mono.just(userResponse));

        Mono<Rent> actual = service.create(rentRequest);
        StepVerifier.create(actual)
                .expectErrorMatches(err-> err instanceof  GenericException )
                .verify();

    }


}