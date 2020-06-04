package com.mycompany.proyecto2.service;

import com.mycompany.proyecto2.domain.Alumno;
import com.mycompany.proyecto2.repository.AlumnoRepository;
import com.mycompany.proyecto2.repository.search.AlumnoSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Alumno}.
 */
@Service
@Transactional
public class AlumnoService {

    private final Logger log = LoggerFactory.getLogger(AlumnoService.class);

    private final AlumnoRepository alumnoRepository;

    private final AlumnoSearchRepository alumnoSearchRepository;

    public AlumnoService(AlumnoRepository alumnoRepository, AlumnoSearchRepository alumnoSearchRepository) {
        this.alumnoRepository = alumnoRepository;
        this.alumnoSearchRepository = alumnoSearchRepository;
    }

    /**
     * Save a alumno.
     *
     * @param alumno the entity to save.
     * @return the persisted entity.
     */
    public Alumno save(Alumno alumno) {
        log.debug("Request to save Alumno : {}", alumno);
        Alumno result = alumnoRepository.save(alumno);
        alumnoSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the alumnos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Alumno> findAll(Pageable pageable) {
        log.debug("Request to get all Alumnos");
        return alumnoRepository.findAll(pageable);
    }


    /**
     *  Get all the alumnos where Expediente is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true) 
    public List<Alumno> findAllWhereExpedienteIsNull() {
        log.debug("Request to get all alumnos where Expediente is null");
        return StreamSupport
            .stream(alumnoRepository.findAll().spliterator(), false)
            .filter(alumno -> alumno.getExpediente() == null)
            .collect(Collectors.toList());
    }

    /**
     * Get one alumno by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Alumno> findOne(Long id) {
        log.debug("Request to get Alumno : {}", id);
        return alumnoRepository.findById(id);
    }

    /**
     * Delete the alumno by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Alumno : {}", id);
        alumnoRepository.deleteById(id);
        alumnoSearchRepository.deleteById(id);
    }
    
    public List<Alumno> getAlumnosNombre(String nombre){
    	return alumnoRepository.findByNombreLike(nombre);
    }

    /**
     * Search for the alumno corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Alumno> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Alumnos for query {}", query);
        return alumnoSearchRepository.search(queryStringQuery(query), pageable);    }
}
