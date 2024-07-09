package com.yunusemrenalbant.user_service.dto;

public record UserResponse(
        Long id,
        String firstName,
        String lastName,
        String email
) { }
