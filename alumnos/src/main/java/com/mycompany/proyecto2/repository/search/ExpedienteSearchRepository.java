package com.mycompany.proyecto2.repository.search;

import com.mycompany.proyecto2.domain.Expediente;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Expediente} entity.
 */
public interface ExpedienteSearchRepository extends ElasticsearchRepository<Expediente, Long> {
}
