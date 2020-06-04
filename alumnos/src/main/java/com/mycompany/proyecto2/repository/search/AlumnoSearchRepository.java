package com.mycompany.proyecto2.repository.search;

import com.mycompany.proyecto2.domain.Alumno;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Alumno} entity.
 */
public interface AlumnoSearchRepository extends ElasticsearchRepository<Alumno, Long> {
}
