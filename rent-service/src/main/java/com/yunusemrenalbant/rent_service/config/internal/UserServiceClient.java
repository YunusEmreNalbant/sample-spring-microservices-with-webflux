package com.yunusemrenalbant.rent_service.config.internal;


import com.yunusemrenalbant.rent_service.domain.UserDataRead;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * bu servis sınıfı qualifier ile ioc container a alınmış olan webclientları kullanarak user mikroservisine
 * atacağımız requestlerin işçi sınıfıdır.
 */
@Component
public class UserServiceClient {
    private final WebClient webClient;

    public UserServiceClient(@Qualifier("userServiceWebClient") WebClient webClient){
        this.webClient = webClient;
    }

    public Mono<UserDataRead> getUserDataWithId(Long id){
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.pathSegment("api", "users", "{id}").build(id))
                .retrieve()
                .bodyToMono(UserDataRead.class);
    }
}
