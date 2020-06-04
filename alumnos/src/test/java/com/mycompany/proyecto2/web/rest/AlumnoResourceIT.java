package com.mycompany.proyecto2.web.rest;

import com.mycompany.proyecto2.AlumnosApp;
import com.mycompany.proyecto2.domain.Alumno;
import com.mycompany.proyecto2.repository.AlumnoRepository;
import com.mycompany.proyecto2.repository.search.AlumnoSearchRepository;
import com.mycompany.proyecto2.service.AlumnoService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link AlumnoResource} REST controller.
 */
@SpringBootTest(classes = AlumnosApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class AlumnoResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    @Autowired
    private AlumnoRepository alumnoRepository;

    @Autowired
    private AlumnoService alumnoService;

    /**
     * This repository is mocked in the com.mycompany.proyecto2.repository.search test package.
     *
     * @see com.mycompany.proyecto2.repository.search.AlumnoSearchRepositoryMockConfiguration
     */
    @Autowired
    private AlumnoSearchRepository mockAlumnoSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAlumnoMockMvc;

    private Alumno alumno;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Alumno createEntity(EntityManager em) {
        Alumno alumno = new Alumno()
            .nombre(DEFAULT_NOMBRE);
        return alumno;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Alumno createUpdatedEntity(EntityManager em) {
        Alumno alumno = new Alumno()
            .nombre(UPDATED_NOMBRE);
        return alumno;
    }

    @BeforeEach
    public void initTest() {
        alumno = createEntity(em);
    }

    @Test
    @Transactional
    public void createAlumno() throws Exception {
        int databaseSizeBeforeCreate = alumnoRepository.findAll().size();

        // Create the Alumno
        restAlumnoMockMvc.perform(post("/api/alumnos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(alumno)))
            .andExpect(status().isCreated());

        // Validate the Alumno in the database
        List<Alumno> alumnoList = alumnoRepository.findAll();
        assertThat(alumnoList).hasSize(databaseSizeBeforeCreate + 1);
        Alumno testAlumno = alumnoList.get(alumnoList.size() - 1);
        assertThat(testAlumno.getNombre()).isEqualTo(DEFAULT_NOMBRE);

        // Validate the Alumno in Elasticsearch
        verify(mockAlumnoSearchRepository, times(1)).save(testAlumno);
    }

    @Test
    @Transactional
    public void createAlumnoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = alumnoRepository.findAll().size();

        // Create the Alumno with an existing ID
        alumno.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAlumnoMockMvc.perform(post("/api/alumnos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(alumno)))
            .andExpect(status().isBadRequest());

        // Validate the Alumno in the database
        List<Alumno> alumnoList = alumnoRepository.findAll();
        assertThat(alumnoList).hasSize(databaseSizeBeforeCreate);

        // Validate the Alumno in Elasticsearch
        verify(mockAlumnoSearchRepository, times(0)).save(alumno);
    }


    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = alumnoRepository.findAll().size();
        // set the field null
        alumno.setNombre(null);

        // Create the Alumno, which fails.

        restAlumnoMockMvc.perform(post("/api/alumnos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(alumno)))
            .andExpect(status().isBadRequest());

        List<Alumno> alumnoList = alumnoRepository.findAll();
        assertThat(alumnoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAlumnos() throws Exception {
        // Initialize the database
        alumnoRepository.saveAndFlush(alumno);

        // Get all the alumnoList
        restAlumnoMockMvc.perform(get("/api/alumnos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(alumno.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)));
    }
    
    @Test
    @Transactional
    public void getAlumno() throws Exception {
        // Initialize the database
        alumnoRepository.saveAndFlush(alumno);

        // Get the alumno
        restAlumnoMockMvc.perform(get("/api/alumnos/{id}", alumno.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(alumno.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE));
    }

    @Test
    @Transactional
    public void getNonExistingAlumno() throws Exception {
        // Get the alumno
        restAlumnoMockMvc.perform(get("/api/alumnos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAlumno() throws Exception {
        // Initialize the database
        alumnoService.save(alumno);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockAlumnoSearchRepository);

        int databaseSizeBeforeUpdate = alumnoRepository.findAll().size();

        // Update the alumno
        Alumno updatedAlumno = alumnoRepository.findById(alumno.getId()).get();
        // Disconnect from session so that the updates on updatedAlumno are not directly saved in db
        em.detach(updatedAlumno);
        updatedAlumno
            .nombre(UPDATED_NOMBRE);

        restAlumnoMockMvc.perform(put("/api/alumnos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedAlumno)))
            .andExpect(status().isOk());

        // Validate the Alumno in the database
        List<Alumno> alumnoList = alumnoRepository.findAll();
        assertThat(alumnoList).hasSize(databaseSizeBeforeUpdate);
        Alumno testAlumno = alumnoList.get(alumnoList.size() - 1);
        assertThat(testAlumno.getNombre()).isEqualTo(UPDATED_NOMBRE);

        // Validate the Alumno in Elasticsearch
        verify(mockAlumnoSearchRepository, times(1)).save(testAlumno);
    }

    @Test
    @Transactional
    public void updateNonExistingAlumno() throws Exception {
        int databaseSizeBeforeUpdate = alumnoRepository.findAll().size();

        // Create the Alumno

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAlumnoMockMvc.perform(put("/api/alumnos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(alumno)))
            .andExpect(status().isBadRequest());

        // Validate the Alumno in the database
        List<Alumno> alumnoList = alumnoRepository.findAll();
        assertThat(alumnoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Alumno in Elasticsearch
        verify(mockAlumnoSearchRepository, times(0)).save(alumno);
    }

    @Test
    @Transactional
    public void deleteAlumno() throws Exception {
        // Initialize the database
        alumnoService.save(alumno);

        int databaseSizeBeforeDelete = alumnoRepository.findAll().size();

        // Delete the alumno
        restAlumnoMockMvc.perform(delete("/api/alumnos/{id}", alumno.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Alumno> alumnoList = alumnoRepository.findAll();
        assertThat(alumnoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Alumno in Elasticsearch
        verify(mockAlumnoSearchRepository, times(1)).deleteById(alumno.getId());
    }

    @Test
    @Transactional
    public void searchAlumno() throws Exception {
        // Initialize the database
        alumnoService.save(alumno);
        when(mockAlumnoSearchRepository.search(queryStringQuery("id:" + alumno.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(alumno), PageRequest.of(0, 1), 1));
        // Search the alumno
        restAlumnoMockMvc.perform(get("/api/_search/alumnos?query=id:" + alumno.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(alumno.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)));
    }
}
