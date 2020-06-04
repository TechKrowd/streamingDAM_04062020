package com.mycompany.proyecto2.repository;

import com.mycompany.proyecto2.domain.Expediente;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Expediente entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ExpedienteRepository extends JpaRepository<Expediente, Long> {
}
