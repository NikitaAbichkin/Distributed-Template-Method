package com.company.Repository;


import org.antlr.v4.runtime.atn.SemanticContext;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.company.Model.WarehouseReservation;

import java.util.Optional;

@Repository
public interface WarehouseReservationRepository extends JpaRepository<WarehouseReservation, Long> {
    Optional<WarehouseReservation> findById(Long id);
    void deleteById(Long id);

}