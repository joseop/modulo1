package com.udea.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ProgramaAcademico.
 */
@Entity
@Table(name = "programa_academico")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProgramaAcademico implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "codigo_programa")
    private Integer codigoPrograma;

    @Column(name = "nombre_programa")
    private String nombrePrograma;

    @Column(name = "codigo_snies")
    private Integer codigoSnies;

    @Column(name = "nombre_encargado")
    private String nombreEncargado;

    @Column(name = "tipo_programa")
    private String tipoPrograma;

    @Column(name = "modalidad_programa")
    private String modalidadPrograma;

    @Column(name = "contactos_ayuda")
    private String contactosAyuda;

    @ManyToOne
    @JsonIgnoreProperties(value = { "programaAcademicos", "unidadAcademica" }, allowSetters = true)
    private SubunidadAcademica subunidadAcademica;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ProgramaAcademico id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCodigoPrograma() {
        return this.codigoPrograma;
    }

    public ProgramaAcademico codigoPrograma(Integer codigoPrograma) {
        this.setCodigoPrograma(codigoPrograma);
        return this;
    }

    public void setCodigoPrograma(Integer codigoPrograma) {
        this.codigoPrograma = codigoPrograma;
    }

    public String getNombrePrograma() {
        return this.nombrePrograma;
    }

    public ProgramaAcademico nombrePrograma(String nombrePrograma) {
        this.setNombrePrograma(nombrePrograma);
        return this;
    }

    public void setNombrePrograma(String nombrePrograma) {
        this.nombrePrograma = nombrePrograma;
    }

    public Integer getCodigoSnies() {
        return this.codigoSnies;
    }

    public ProgramaAcademico codigoSnies(Integer codigoSnies) {
        this.setCodigoSnies(codigoSnies);
        return this;
    }

    public void setCodigoSnies(Integer codigoSnies) {
        this.codigoSnies = codigoSnies;
    }

    public String getNombreEncargado() {
        return this.nombreEncargado;
    }

    public ProgramaAcademico nombreEncargado(String nombreEncargado) {
        this.setNombreEncargado(nombreEncargado);
        return this;
    }

    public void setNombreEncargado(String nombreEncargado) {
        this.nombreEncargado = nombreEncargado;
    }

    public String getTipoPrograma() {
        return this.tipoPrograma;
    }

    public ProgramaAcademico tipoPrograma(String tipoPrograma) {
        this.setTipoPrograma(tipoPrograma);
        return this;
    }

    public void setTipoPrograma(String tipoPrograma) {
        this.tipoPrograma = tipoPrograma;
    }

    public String getModalidadPrograma() {
        return this.modalidadPrograma;
    }

    public ProgramaAcademico modalidadPrograma(String modalidadPrograma) {
        this.setModalidadPrograma(modalidadPrograma);
        return this;
    }

    public void setModalidadPrograma(String modalidadPrograma) {
        this.modalidadPrograma = modalidadPrograma;
    }

    public String getContactosAyuda() {
        return this.contactosAyuda;
    }

    public ProgramaAcademico contactosAyuda(String contactosAyuda) {
        this.setContactosAyuda(contactosAyuda);
        return this;
    }

    public void setContactosAyuda(String contactosAyuda) {
        this.contactosAyuda = contactosAyuda;
    }

    public SubunidadAcademica getSubunidadAcademica() {
        return this.subunidadAcademica;
    }

    public void setSubunidadAcademica(SubunidadAcademica subunidadAcademica) {
        this.subunidadAcademica = subunidadAcademica;
    }

    public ProgramaAcademico subunidadAcademica(SubunidadAcademica subunidadAcademica) {
        this.setSubunidadAcademica(subunidadAcademica);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProgramaAcademico)) {
            return false;
        }
        return id != null && id.equals(((ProgramaAcademico) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProgramaAcademico{" +
            "id=" + getId() +
            ", codigoPrograma=" + getCodigoPrograma() +
            ", nombrePrograma='" + getNombrePrograma() + "'" +
            ", codigoSnies=" + getCodigoSnies() +
            ", nombreEncargado='" + getNombreEncargado() + "'" +
            ", tipoPrograma='" + getTipoPrograma() + "'" +
            ", modalidadPrograma='" + getModalidadPrograma() + "'" +
            ", contactosAyuda='" + getContactosAyuda() + "'" +
            "}";
    }
}
