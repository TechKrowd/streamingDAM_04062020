package com.mycompany.proyecto2.repository;

import com.mycompany.proyecto2.domain.Alumno;

import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Alumno entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AlumnoRepository extends JpaRepository<Alumno, Long> {
	List<Alumno> findByNombreLike(String nombre);
	List<Alumno> findByExpediente_numero(String expediente);
}
