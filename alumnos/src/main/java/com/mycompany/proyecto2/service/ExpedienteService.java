package com.mycompany.proyecto2.service;

import com.mycompany.proyecto2.domain.Expediente;
import com.mycompany.proyecto2.repository.ExpedienteRepository;
import com.mycompany.proyecto2.repository.search.ExpedienteSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Expediente}.
 */
@Service
@Transactional
public class ExpedienteService {

    private final Logger log = LoggerFactory.getLogger(ExpedienteService.class);

    private final ExpedienteRepository expedienteRepository;

    private final ExpedienteSearchRepository expedienteSearchRepository;

    public ExpedienteService(ExpedienteRepository expedienteRepository, ExpedienteSearchRepository expedienteSearchRepository) {
        this.expedienteRepository = expedienteRepository;
        this.expedienteSearchRepository = expedienteSearchRepository;
    }

    /**
     * Save a expediente.
     *
     * @param expediente the entity to save.
     * @return the persisted entity.
     */
    public Expediente save(Expediente expediente) {
        log.debug("Request to save Expediente : {}", expediente);
        Expediente result = expedienteRepository.save(expediente);
        expedienteSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the expedientes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Expediente> findAll(Pageable pageable) {
        log.debug("Request to get all Expedientes");
        return expedienteRepository.findAll(pageable);
    }

    /**
     * Get one expediente by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Expediente> findOne(Long id) {
        log.debug("Request to get Expediente : {}", id);
        return expedienteRepository.findById(id);
    }

    /**
     * Delete the expediente by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Expediente : {}", id);
        expedienteRepository.deleteById(id);
        expedienteSearchRepository.deleteById(id);
    }

    /**
     * Search for the expediente corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Expediente> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Expedientes for query {}", query);
        return expedienteSearchRepository.search(queryStringQuery(query), pageable);    }
}
