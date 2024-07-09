package com.yunusemrenalbant.user_service.dto;

public record UserRequest(
        Long id,
        String firstName,
        String lastName,
        String email
) { }