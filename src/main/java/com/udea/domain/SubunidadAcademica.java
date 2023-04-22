package com.udea.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A SubunidadAcademica.
 */
@Entity
@Table(name = "subunidad_academica")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SubunidadAcademica implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "codigo_subunidad")
    private Integer codigoSubunidad;

    @Column(name = "nombre_subunidad")
    private String nombreSubunidad;

    @Column(name = "tipo_subunidad")
    private String tipoSubunidad;

    @Column(name = "nombre_jefe")
    private String nombreJefe;

    @Column(name = "descripcion")
    private String descripcion;

    @OneToMany(mappedBy = "subunidadAcademica")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "subunidadAcademica" }, allowSetters = true)
    private Set<ProgramaAcademico> programaAcademicos = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "subunidadAcademicas" }, allowSetters = true)
    private UnidadAcademica unidadAcademica;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public SubunidadAcademica id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCodigoSubunidad() {
        return this.codigoSubunidad;
    }

    public SubunidadAcademica codigoSubunidad(Integer codigoSubunidad) {
        this.setCodigoSubunidad(codigoSubunidad);
        return this;
    }

    public void setCodigoSubunidad(Integer codigoSubunidad) {
        this.codigoSubunidad = codigoSubunidad;
    }

    public String getNombreSubunidad() {
        return this.nombreSubunidad;
    }

    public SubunidadAcademica nombreSubunidad(String nombreSubunidad) {
        this.setNombreSubunidad(nombreSubunidad);
        return this;
    }

    public void setNombreSubunidad(String nombreSubunidad) {
        this.nombreSubunidad = nombreSubunidad;
    }

    public String getTipoSubunidad() {
        return this.tipoSubunidad;
    }

    public SubunidadAcademica tipoSubunidad(String tipoSubunidad) {
        this.setTipoSubunidad(tipoSubunidad);
        return this;
    }

    public void setTipoSubunidad(String tipoSubunidad) {
        this.tipoSubunidad = tipoSubunidad;
    }

    public String getNombreJefe() {
        return this.nombreJefe;
    }

    public SubunidadAcademica nombreJefe(String nombreJefe) {
        this.setNombreJefe(nombreJefe);
        return this;
    }

    public void setNombreJefe(String nombreJefe) {
        this.nombreJefe = nombreJefe;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public SubunidadAcademica descripcion(String descripcion) {
        this.setDescripcion(descripcion);
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Set<ProgramaAcademico> getProgramaAcademicos() {
        return this.programaAcademicos;
    }

    public void setProgramaAcademicos(Set<ProgramaAcademico> programaAcademicos) {
        if (this.programaAcademicos != null) {
            this.programaAcademicos.forEach(i -> i.setSubunidadAcademica(null));
        }
        if (programaAcademicos != null) {
            programaAcademicos.forEach(i -> i.setSubunidadAcademica(this));
        }
        this.programaAcademicos = programaAcademicos;
    }

    public SubunidadAcademica programaAcademicos(Set<ProgramaAcademico> programaAcademicos) {
        this.setProgramaAcademicos(programaAcademicos);
        return this;
    }

    public SubunidadAcademica addProgramaAcademico(ProgramaAcademico programaAcademico) {
        this.programaAcademicos.add(programaAcademico);
        programaAcademico.setSubunidadAcademica(this);
        return this;
    }

    public SubunidadAcademica removeProgramaAcademico(ProgramaAcademico programaAcademico) {
        this.programaAcademicos.remove(programaAcademico);
        programaAcademico.setSubunidadAcademica(null);
        return this;
    }

    public UnidadAcademica getUnidadAcademica() {
        return this.unidadAcademica;
    }

    public void setUnidadAcademica(UnidadAcademica unidadAcademica) {
        this.unidadAcademica = unidadAcademica;
    }

    public SubunidadAcademica unidadAcademica(UnidadAcademica unidadAcademica) {
        this.setUnidadAcademica(unidadAcademica);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SubunidadAcademica)) {
            return false;
        }
        return id != null && id.equals(((SubunidadAcademica) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SubunidadAcademica{" +
            "id=" + getId() +
            ", codigoSubunidad=" + getCodigoSubunidad() +
            ", nombreSubunidad='" + getNombreSubunidad() + "'" +
            ", tipoSubunidad='" + getTipoSubunidad() + "'" +
            ", nombreJefe='" + getNombreJefe() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            "}";
    }
}
