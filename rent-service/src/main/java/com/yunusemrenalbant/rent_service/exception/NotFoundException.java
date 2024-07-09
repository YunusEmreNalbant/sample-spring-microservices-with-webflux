package com.yunusemrenalbant.rent_service.exception;

public class NotFoundException extends RuntimeException{
   public NotFoundException(String message) {
       super(message);
   }
}
