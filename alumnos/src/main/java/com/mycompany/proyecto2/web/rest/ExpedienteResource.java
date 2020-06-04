package com.mycompany.proyecto2.web.rest;

import com.mycompany.proyecto2.domain.Expediente;
import com.mycompany.proyecto2.service.ExpedienteService;
import com.mycompany.proyecto2.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link com.mycompany.proyecto2.domain.Expediente}.
 */
@RestController
@RequestMapping("/api")
public class ExpedienteResource {

    private final Logger log = LoggerFactory.getLogger(ExpedienteResource.class);

    private static final String ENTITY_NAME = "expediente";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ExpedienteService expedienteService;

    public ExpedienteResource(ExpedienteService expedienteService) {
        this.expedienteService = expedienteService;
    }

    /**
     * {@code POST  /expedientes} : Create a new expediente.
     *
     * @param expediente the expediente to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new expediente, or with status {@code 400 (Bad Request)} if the expediente has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/expedientes")
    public ResponseEntity<Expediente> createExpediente(@Valid @RequestBody Expediente expediente) throws URISyntaxException {
        log.debug("REST request to save Expediente : {}", expediente);
        if (expediente.getId() != null) {
            throw new BadRequestAlertException("A new expediente cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Expediente result = expedienteService.save(expediente);
        return ResponseEntity.created(new URI("/api/expedientes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /expedientes} : Updates an existing expediente.
     *
     * @param expediente the expediente to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated expediente,
     * or with status {@code 400 (Bad Request)} if the expediente is not valid,
     * or with status {@code 500 (Internal Server Error)} if the expediente couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/expedientes")
    public ResponseEntity<Expediente> updateExpediente(@Valid @RequestBody Expediente expediente) throws URISyntaxException {
        log.debug("REST request to update Expediente : {}", expediente);
        if (expediente.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Expediente result = expedienteService.save(expediente);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, expediente.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /expedientes} : get all the expedientes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of expedientes in body.
     */
    @GetMapping("/expedientes")
    public ResponseEntity<List<Expediente>> getAllExpedientes(Pageable pageable) {
        log.debug("REST request to get a page of Expedientes");
        Page<Expediente> page = expedienteService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /expedientes/:id} : get the "id" expediente.
     *
     * @param id the id of the expediente to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the expediente, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/expedientes/{id}")
    public ResponseEntity<Expediente> getExpediente(@PathVariable Long id) {
        log.debug("REST request to get Expediente : {}", id);
        Optional<Expediente> expediente = expedienteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(expediente);
    }

    /**
     * {@code DELETE  /expedientes/:id} : delete the "id" expediente.
     *
     * @param id the id of the expediente to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/expedientes/{id}")
    public ResponseEntity<Void> deleteExpediente(@PathVariable Long id) {
        log.debug("REST request to delete Expediente : {}", id);
        expedienteService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/expedientes?query=:query} : search for the expediente corresponding
     * to the query.
     *
     * @param query the query of the expediente search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/expedientes")
    public ResponseEntity<List<Expediente>> searchExpedientes(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Expedientes for query {}", query);
        Page<Expediente> page = expedienteService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
