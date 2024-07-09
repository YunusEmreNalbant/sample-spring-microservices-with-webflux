package com.yunusemrenalbant.rent_service.config.internal;


import com.yunusemrenalbant.rent_service.domain.VehicleDataRead;
import com.yunusemrenalbant.rent_service.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * bu servis sınıfı qualifier ile ioc container a alınmış olan webclientları kullanarak vehicle mikroservisine
 * atacağımız requestlerin işçi sınıfıdır.
 */
@Component
public class VehicleServiceClient {
    private final WebClient webClient;

    public VehicleServiceClient(@Qualifier("vehicleServiceWebClient") WebClient webClient){
        this.webClient=webClient;
    }

    public Mono<VehicleDataRead> getVehicleDataWithId(Long id){
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.pathSegment("api","vehicles","{id}").build(id))
                .retrieve()
                .bodyToMono(VehicleDataRead.class)
                .switchIfEmpty(Mono.error(new NotFoundException("Vehicle bulunamadı id:"+id.toString())))
                .onErrorResume(err->Mono.error(new NotFoundException("Vehicle bulunamadı id:"+ id)));
    }
}
