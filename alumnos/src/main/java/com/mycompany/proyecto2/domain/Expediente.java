package com.mycompany.proyecto2.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Expediente.
 */
@Entity
@Table(name = "expediente")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "expediente")
public class Expediente implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 5, max = 5)
    @Column(name = "numero", length = 5, nullable = false, unique = true)
    private String numero;

    @OneToOne
    @JoinColumn(unique = true)
    private Alumno alumno;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public Expediente numero(String numero) {
        this.numero = numero;
        return this;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Alumno getAlumno() {
        return alumno;
    }

    public Expediente alumno(Alumno alumno) {
        this.alumno = alumno;
        return this;
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Expediente)) {
            return false;
        }
        return id != null && id.equals(((Expediente) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Expediente{" +
            "id=" + getId() +
            ", numero='" + getNumero() + "'" +
            "}";
    }
}
