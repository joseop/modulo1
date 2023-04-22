package com.udea.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A UnidadAcademica.
 */
@Entity
@Table(name = "unidad_academica")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class UnidadAcademica implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "codigo_unidad")
    private Integer codigoUnidad;

    @Column(name = "nombre_unidad")
    private String nombreUnidad;

    @Column(name = "tipo_unidad")
    private String tipoUnidad;

    @Column(name = "nombre_decano")
    private String nombreDecano;

    @Column(name = "descripcion")
    private String descripcion;

    @OneToMany(mappedBy = "unidadAcademica")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "programaAcademicos", "unidadAcademica" }, allowSetters = true)
    private Set<SubunidadAcademica> subunidadAcademicas = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public UnidadAcademica id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCodigoUnidad() {
        return this.codigoUnidad;
    }

    public UnidadAcademica codigoUnidad(Integer codigoUnidad) {
        this.setCodigoUnidad(codigoUnidad);
        return this;
    }

    public void setCodigoUnidad(Integer codigoUnidad) {
        this.codigoUnidad = codigoUnidad;
    }

    public String getNombreUnidad() {
        return this.nombreUnidad;
    }

    public UnidadAcademica nombreUnidad(String nombreUnidad) {
        this.setNombreUnidad(nombreUnidad);
        return this;
    }

    public void setNombreUnidad(String nombreUnidad) {
        this.nombreUnidad = nombreUnidad;
    }

    public String getTipoUnidad() {
        return this.tipoUnidad;
    }

    public UnidadAcademica tipoUnidad(String tipoUnidad) {
        this.setTipoUnidad(tipoUnidad);
        return this;
    }

    public void setTipoUnidad(String tipoUnidad) {
        this.tipoUnidad = tipoUnidad;
    }

    public String getNombreDecano() {
        return this.nombreDecano;
    }

    public UnidadAcademica nombreDecano(String nombreDecano) {
        this.setNombreDecano(nombreDecano);
        return this;
    }

    public void setNombreDecano(String nombreDecano) {
        this.nombreDecano = nombreDecano;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public UnidadAcademica descripcion(String descripcion) {
        this.setDescripcion(descripcion);
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Set<SubunidadAcademica> getSubunidadAcademicas() {
        return this.subunidadAcademicas;
    }

    public void setSubunidadAcademicas(Set<SubunidadAcademica> subunidadAcademicas) {
        if (this.subunidadAcademicas != null) {
            this.subunidadAcademicas.forEach(i -> i.setUnidadAcademica(null));
        }
        if (subunidadAcademicas != null) {
            subunidadAcademicas.forEach(i -> i.setUnidadAcademica(this));
        }
        this.subunidadAcademicas = subunidadAcademicas;
    }

    public UnidadAcademica subunidadAcademicas(Set<SubunidadAcademica> subunidadAcademicas) {
        this.setSubunidadAcademicas(subunidadAcademicas);
        return this;
    }

    public UnidadAcademica addSubunidadAcademica(SubunidadAcademica subunidadAcademica) {
        this.subunidadAcademicas.add(subunidadAcademica);
        subunidadAcademica.setUnidadAcademica(this);
        return this;
    }

    public UnidadAcademica removeSubunidadAcademica(SubunidadAcademica subunidadAcademica) {
        this.subunidadAcademicas.remove(subunidadAcademica);
        subunidadAcademica.setUnidadAcademica(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UnidadAcademica)) {
            return false;
        }
        return id != null && id.equals(((UnidadAcademica) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UnidadAcademica{" +
            "id=" + getId() +
            ", codigoUnidad=" + getCodigoUnidad() +
            ", nombreUnidad='" + getNombreUnidad() + "'" +
            ", tipoUnidad='" + getTipoUnidad() + "'" +
            ", nombreDecano='" + getNombreDecano() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            "}";
    }
}
