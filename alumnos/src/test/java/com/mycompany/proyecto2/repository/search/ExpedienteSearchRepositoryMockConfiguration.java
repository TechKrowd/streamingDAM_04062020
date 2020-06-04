package com.mycompany.proyecto2.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link ExpedienteSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class ExpedienteSearchRepositoryMockConfiguration {

    @MockBean
    private ExpedienteSearchRepository mockExpedienteSearchRepository;

}
