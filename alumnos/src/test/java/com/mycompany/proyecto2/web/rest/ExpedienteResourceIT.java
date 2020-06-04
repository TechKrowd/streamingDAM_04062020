package com.mycompany.proyecto2.web.rest;

import com.mycompany.proyecto2.AlumnosApp;
import com.mycompany.proyecto2.domain.Expediente;
import com.mycompany.proyecto2.repository.ExpedienteRepository;
import com.mycompany.proyecto2.repository.search.ExpedienteSearchRepository;
import com.mycompany.proyecto2.service.ExpedienteService;

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
 * Integration tests for the {@link ExpedienteResource} REST controller.
 */
@SpringBootTest(classes = AlumnosApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class ExpedienteResourceIT {

    private static final String DEFAULT_NUMERO = "AAAAA";
    private static final String UPDATED_NUMERO = "BBBBB";

    @Autowired
    private ExpedienteRepository expedienteRepository;

    @Autowired
    private ExpedienteService expedienteService;

    /**
     * This repository is mocked in the com.mycompany.proyecto2.repository.search test package.
     *
     * @see com.mycompany.proyecto2.repository.search.ExpedienteSearchRepositoryMockConfiguration
     */
    @Autowired
    private ExpedienteSearchRepository mockExpedienteSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restExpedienteMockMvc;

    private Expediente expediente;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Expediente createEntity(EntityManager em) {
        Expediente expediente = new Expediente()
            .numero(DEFAULT_NUMERO);
        return expediente;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Expediente createUpdatedEntity(EntityManager em) {
        Expediente expediente = new Expediente()
            .numero(UPDATED_NUMERO);
        return expediente;
    }

    @BeforeEach
    public void initTest() {
        expediente = createEntity(em);
    }

    @Test
    @Transactional
    public void createExpediente() throws Exception {
        int databaseSizeBeforeCreate = expedienteRepository.findAll().size();

        // Create the Expediente
        restExpedienteMockMvc.perform(post("/api/expedientes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(expediente)))
            .andExpect(status().isCreated());

        // Validate the Expediente in the database
        List<Expediente> expedienteList = expedienteRepository.findAll();
        assertThat(expedienteList).hasSize(databaseSizeBeforeCreate + 1);
        Expediente testExpediente = expedienteList.get(expedienteList.size() - 1);
        assertThat(testExpediente.getNumero()).isEqualTo(DEFAULT_NUMERO);

        // Validate the Expediente in Elasticsearch
        verify(mockExpedienteSearchRepository, times(1)).save(testExpediente);
    }

    @Test
    @Transactional
    public void createExpedienteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = expedienteRepository.findAll().size();

        // Create the Expediente with an existing ID
        expediente.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restExpedienteMockMvc.perform(post("/api/expedientes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(expediente)))
            .andExpect(status().isBadRequest());

        // Validate the Expediente in the database
        List<Expediente> expedienteList = expedienteRepository.findAll();
        assertThat(expedienteList).hasSize(databaseSizeBeforeCreate);

        // Validate the Expediente in Elasticsearch
        verify(mockExpedienteSearchRepository, times(0)).save(expediente);
    }


    @Test
    @Transactional
    public void checkNumeroIsRequired() throws Exception {
        int databaseSizeBeforeTest = expedienteRepository.findAll().size();
        // set the field null
        expediente.setNumero(null);

        // Create the Expediente, which fails.

        restExpedienteMockMvc.perform(post("/api/expedientes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(expediente)))
            .andExpect(status().isBadRequest());

        List<Expediente> expedienteList = expedienteRepository.findAll();
        assertThat(expedienteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllExpedientes() throws Exception {
        // Initialize the database
        expedienteRepository.saveAndFlush(expediente);

        // Get all the expedienteList
        restExpedienteMockMvc.perform(get("/api/expedientes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(expediente.getId().intValue())))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)));
    }
    
    @Test
    @Transactional
    public void getExpediente() throws Exception {
        // Initialize the database
        expedienteRepository.saveAndFlush(expediente);

        // Get the expediente
        restExpedienteMockMvc.perform(get("/api/expedientes/{id}", expediente.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(expediente.getId().intValue()))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO));
    }

    @Test
    @Transactional
    public void getNonExistingExpediente() throws Exception {
        // Get the expediente
        restExpedienteMockMvc.perform(get("/api/expedientes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateExpediente() throws Exception {
        // Initialize the database
        expedienteService.save(expediente);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockExpedienteSearchRepository);

        int databaseSizeBeforeUpdate = expedienteRepository.findAll().size();

        // Update the expediente
        Expediente updatedExpediente = expedienteRepository.findById(expediente.getId()).get();
        // Disconnect from session so that the updates on updatedExpediente are not directly saved in db
        em.detach(updatedExpediente);
        updatedExpediente
            .numero(UPDATED_NUMERO);

        restExpedienteMockMvc.perform(put("/api/expedientes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedExpediente)))
            .andExpect(status().isOk());

        // Validate the Expediente in the database
        List<Expediente> expedienteList = expedienteRepository.findAll();
        assertThat(expedienteList).hasSize(databaseSizeBeforeUpdate);
        Expediente testExpediente = expedienteList.get(expedienteList.size() - 1);
        assertThat(testExpediente.getNumero()).isEqualTo(UPDATED_NUMERO);

        // Validate the Expediente in Elasticsearch
        verify(mockExpedienteSearchRepository, times(1)).save(testExpediente);
    }

    @Test
    @Transactional
    public void updateNonExistingExpediente() throws Exception {
        int databaseSizeBeforeUpdate = expedienteRepository.findAll().size();

        // Create the Expediente

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restExpedienteMockMvc.perform(put("/api/expedientes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(expediente)))
            .andExpect(status().isBadRequest());

        // Validate the Expediente in the database
        List<Expediente> expedienteList = expedienteRepository.findAll();
        assertThat(expedienteList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Expediente in Elasticsearch
        verify(mockExpedienteSearchRepository, times(0)).save(expediente);
    }

    @Test
    @Transactional
    public void deleteExpediente() throws Exception {
        // Initialize the database
        expedienteService.save(expediente);

        int databaseSizeBeforeDelete = expedienteRepository.findAll().size();

        // Delete the expediente
        restExpedienteMockMvc.perform(delete("/api/expedientes/{id}", expediente.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Expediente> expedienteList = expedienteRepository.findAll();
        assertThat(expedienteList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Expediente in Elasticsearch
        verify(mockExpedienteSearchRepository, times(1)).deleteById(expediente.getId());
    }

    @Test
    @Transactional
    public void searchExpediente() throws Exception {
        // Initialize the database
        expedienteService.save(expediente);
        when(mockExpedienteSearchRepository.search(queryStringQuery("id:" + expediente.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(expediente), PageRequest.of(0, 1), 1));
        // Search the expediente
        restExpedienteMockMvc.perform(get("/api/_search/expedientes?query=id:" + expediente.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(expediente.getId().intValue())))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)));
    }
}
