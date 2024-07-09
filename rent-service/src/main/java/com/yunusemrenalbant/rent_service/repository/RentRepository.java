package com.yunusemrenalbant.rent_service.repository;

import com.yunusemrenalbant.rent_service.model.Rent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentRepository extends JpaRepository<Rent, Long> {
}
