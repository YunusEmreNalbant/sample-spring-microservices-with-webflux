package com.yunusemrenalbant.rent_service.domain;

import lombok.Builder;

@Builder
public record UserDataRead(
        Long id,
        String firstName,
        String lastName,
        String email
) { }
