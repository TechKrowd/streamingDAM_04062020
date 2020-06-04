package com.mycompany.proyecto2.web.rest;

import com.mycompany.proyecto2.domain.Alumno;
import com.mycompany.proyecto2.service.AlumnoService;
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
 * REST controller for managing {@link com.mycompany.proyecto2.domain.Alumno}.
 */
@RestController
@RequestMapping("/api")
public class AlumnoResource {

    private final Logger log = LoggerFactory.getLogger(AlumnoResource.class);

    private static final String ENTITY_NAME = "alumno";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AlumnoService alumnoService;

    public AlumnoResource(AlumnoService alumnoService) {
        this.alumnoService = alumnoService;
    }

    /**
     * {@code POST  /alumnos} : Create a new alumno.
     *
     * @param alumno the alumno to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new alumno, or with status {@code 400 (Bad Request)} if the alumno has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/alumnos")
    public ResponseEntity<Alumno> createAlumno(@Valid @RequestBody Alumno alumno) throws URISyntaxException {
        log.debug("REST request to save Alumno : {}", alumno);
        if (alumno.getId() != null) {
            throw new BadRequestAlertException("A new alumno cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Alumno result = alumnoService.save(alumno);
        return ResponseEntity.created(new URI("/api/alumnos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /alumnos} : Updates an existing alumno.
     *
     * @param alumno the alumno to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated alumno,
     * or with status {@code 400 (Bad Request)} if the alumno is not valid,
     * or with status {@code 500 (Internal Server Error)} if the alumno couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/alumnos")
    public ResponseEntity<Alumno> updateAlumno(@Valid @RequestBody Alumno alumno) throws URISyntaxException {
        log.debug("REST request to update Alumno : {}", alumno);
        if (alumno.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Alumno result = alumnoService.save(alumno);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, alumno.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /alumnos} : get all the alumnos.
     *
     * @param pageable the pagination information.
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of alumnos in body.
     */
    @GetMapping("/alumnos")
    public ResponseEntity<List<Alumno>> getAllAlumnos(Pageable pageable, @RequestParam(required = false) String filter) {
        if ("expediente-is-null".equals(filter)) {
            log.debug("REST request to get all Alumnos where expediente is null");
            return new ResponseEntity<>(alumnoService.findAllWhereExpedienteIsNull(),
                    HttpStatus.OK);
        }
        log.debug("REST request to get a page of Alumnos");
        Page<Alumno> page = alumnoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /alumnos/:id} : get the "id" alumno.
     *
     * @param id the id of the alumno to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the alumno, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/alumnos/{id}")
    public ResponseEntity<Alumno> getAlumno(@PathVariable Long id) {
        log.debug("REST request to get Alumno : {}", id);
        Optional<Alumno> alumno = alumnoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(alumno);
    }

    /**
     * {@code DELETE  /alumnos/:id} : delete the "id" alumno.
     *
     * @param id the id of the alumno to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/alumnos/{id}")
    public ResponseEntity<Void> deleteAlumno(@PathVariable Long id) {
        log.debug("REST request to delete Alumno : {}", id);
        alumnoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/alumnos?query=:query} : search for the alumno corresponding
     * to the query.
     *
     * @param query the query of the alumno search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/alumnos")
    public ResponseEntity<List<Alumno>> searchAlumnos(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Alumnos for query {}", query);
        Page<Alumno> page = alumnoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
    
    @GetMapping("/alumnonombre")
    public List<Alumno> getAlumnosNombre(@RequestParam(name="nombre") String nombre){
    	return alumnoService.getAlumnosNombre(nombre);
    }
}
