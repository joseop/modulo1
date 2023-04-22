package com.udea.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.udea.IntegrationTest;
import com.udea.domain.ProgramaAcademico;
import com.udea.repository.ProgramaAcademicoRepository;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ProgramaAcademicoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ProgramaAcademicoResourceIT {

    private static final Integer DEFAULT_CODIGO_PROGRAMA = 1;
    private static final Integer UPDATED_CODIGO_PROGRAMA = 2;

    private static final String DEFAULT_NOMBRE_PROGRAMA = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE_PROGRAMA = "BBBBBBBBBB";

    private static final Integer DEFAULT_CODIGO_SNIES = 1;
    private static final Integer UPDATED_CODIGO_SNIES = 2;

    private static final String DEFAULT_NOMBRE_ENCARGADO = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE_ENCARGADO = "BBBBBBBBBB";

    private static final String DEFAULT_TIPO_PROGRAMA = "AAAAAAAAAA";
    private static final String UPDATED_TIPO_PROGRAMA = "BBBBBBBBBB";

    private static final String DEFAULT_MODALIDAD_PROGRAMA = "AAAAAAAAAA";
    private static final String UPDATED_MODALIDAD_PROGRAMA = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACTOS_AYUDA = "AAAAAAAAAA";
    private static final String UPDATED_CONTACTOS_AYUDA = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/programa-academicos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProgramaAcademicoRepository programaAcademicoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProgramaAcademicoMockMvc;

    private ProgramaAcademico programaAcademico;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProgramaAcademico createEntity(EntityManager em) {
        ProgramaAcademico programaAcademico = new ProgramaAcademico()
            .codigoPrograma(DEFAULT_CODIGO_PROGRAMA)
            .nombrePrograma(DEFAULT_NOMBRE_PROGRAMA)
            .codigoSnies(DEFAULT_CODIGO_SNIES)
            .nombreEncargado(DEFAULT_NOMBRE_ENCARGADO)
            .tipoPrograma(DEFAULT_TIPO_PROGRAMA)
            .modalidadPrograma(DEFAULT_MODALIDAD_PROGRAMA)
            .contactosAyuda(DEFAULT_CONTACTOS_AYUDA);
        return programaAcademico;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProgramaAcademico createUpdatedEntity(EntityManager em) {
        ProgramaAcademico programaAcademico = new ProgramaAcademico()
            .codigoPrograma(UPDATED_CODIGO_PROGRAMA)
            .nombrePrograma(UPDATED_NOMBRE_PROGRAMA)
            .codigoSnies(UPDATED_CODIGO_SNIES)
            .nombreEncargado(UPDATED_NOMBRE_ENCARGADO)
            .tipoPrograma(UPDATED_TIPO_PROGRAMA)
            .modalidadPrograma(UPDATED_MODALIDAD_PROGRAMA)
            .contactosAyuda(UPDATED_CONTACTOS_AYUDA);
        return programaAcademico;
    }

    @BeforeEach
    public void initTest() {
        programaAcademico = createEntity(em);
    }

    @Test
    @Transactional
    void createProgramaAcademico() throws Exception {
        int databaseSizeBeforeCreate = programaAcademicoRepository.findAll().size();
        // Create the ProgramaAcademico
        restProgramaAcademicoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(programaAcademico))
            )
            .andExpect(status().isCreated());

        // Validate the ProgramaAcademico in the database
        List<ProgramaAcademico> programaAcademicoList = programaAcademicoRepository.findAll();
        assertThat(programaAcademicoList).hasSize(databaseSizeBeforeCreate + 1);
        ProgramaAcademico testProgramaAcademico = programaAcademicoList.get(programaAcademicoList.size() - 1);
        assertThat(testProgramaAcademico.getCodigoPrograma()).isEqualTo(DEFAULT_CODIGO_PROGRAMA);
        assertThat(testProgramaAcademico.getNombrePrograma()).isEqualTo(DEFAULT_NOMBRE_PROGRAMA);
        assertThat(testProgramaAcademico.getCodigoSnies()).isEqualTo(DEFAULT_CODIGO_SNIES);
        assertThat(testProgramaAcademico.getNombreEncargado()).isEqualTo(DEFAULT_NOMBRE_ENCARGADO);
        assertThat(testProgramaAcademico.getTipoPrograma()).isEqualTo(DEFAULT_TIPO_PROGRAMA);
        assertThat(testProgramaAcademico.getModalidadPrograma()).isEqualTo(DEFAULT_MODALIDAD_PROGRAMA);
        assertThat(testProgramaAcademico.getContactosAyuda()).isEqualTo(DEFAULT_CONTACTOS_AYUDA);
    }

    @Test
    @Transactional
    void createProgramaAcademicoWithExistingId() throws Exception {
        // Create the ProgramaAcademico with an existing ID
        programaAcademico.setId(1L);

        int databaseSizeBeforeCreate = programaAcademicoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProgramaAcademicoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(programaAcademico))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProgramaAcademico in the database
        List<ProgramaAcademico> programaAcademicoList = programaAcademicoRepository.findAll();
        assertThat(programaAcademicoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllProgramaAcademicos() throws Exception {
        // Initialize the database
        programaAcademicoRepository.saveAndFlush(programaAcademico);

        // Get all the programaAcademicoList
        restProgramaAcademicoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(programaAcademico.getId().intValue())))
            .andExpect(jsonPath("$.[*].codigoPrograma").value(hasItem(DEFAULT_CODIGO_PROGRAMA)))
            .andExpect(jsonPath("$.[*].nombrePrograma").value(hasItem(DEFAULT_NOMBRE_PROGRAMA)))
            .andExpect(jsonPath("$.[*].codigoSnies").value(hasItem(DEFAULT_CODIGO_SNIES)))
            .andExpect(jsonPath("$.[*].nombreEncargado").value(hasItem(DEFAULT_NOMBRE_ENCARGADO)))
            .andExpect(jsonPath("$.[*].tipoPrograma").value(hasItem(DEFAULT_TIPO_PROGRAMA)))
            .andExpect(jsonPath("$.[*].modalidadPrograma").value(hasItem(DEFAULT_MODALIDAD_PROGRAMA)))
            .andExpect(jsonPath("$.[*].contactosAyuda").value(hasItem(DEFAULT_CONTACTOS_AYUDA)));
    }

    @Test
    @Transactional
    void getProgramaAcademico() throws Exception {
        // Initialize the database
        programaAcademicoRepository.saveAndFlush(programaAcademico);

        // Get the programaAcademico
        restProgramaAcademicoMockMvc
            .perform(get(ENTITY_API_URL_ID, programaAcademico.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(programaAcademico.getId().intValue()))
            .andExpect(jsonPath("$.codigoPrograma").value(DEFAULT_CODIGO_PROGRAMA))
            .andExpect(jsonPath("$.nombrePrograma").value(DEFAULT_NOMBRE_PROGRAMA))
            .andExpect(jsonPath("$.codigoSnies").value(DEFAULT_CODIGO_SNIES))
            .andExpect(jsonPath("$.nombreEncargado").value(DEFAULT_NOMBRE_ENCARGADO))
            .andExpect(jsonPath("$.tipoPrograma").value(DEFAULT_TIPO_PROGRAMA))
            .andExpect(jsonPath("$.modalidadPrograma").value(DEFAULT_MODALIDAD_PROGRAMA))
            .andExpect(jsonPath("$.contactosAyuda").value(DEFAULT_CONTACTOS_AYUDA));
    }

    @Test
    @Transactional
    void getNonExistingProgramaAcademico() throws Exception {
        // Get the programaAcademico
        restProgramaAcademicoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingProgramaAcademico() throws Exception {
        // Initialize the database
        programaAcademicoRepository.saveAndFlush(programaAcademico);

        int databaseSizeBeforeUpdate = programaAcademicoRepository.findAll().size();

        // Update the programaAcademico
        ProgramaAcademico updatedProgramaAcademico = programaAcademicoRepository.findById(programaAcademico.getId()).get();
        // Disconnect from session so that the updates on updatedProgramaAcademico are not directly saved in db
        em.detach(updatedProgramaAcademico);
        updatedProgramaAcademico
            .codigoPrograma(UPDATED_CODIGO_PROGRAMA)
            .nombrePrograma(UPDATED_NOMBRE_PROGRAMA)
            .codigoSnies(UPDATED_CODIGO_SNIES)
            .nombreEncargado(UPDATED_NOMBRE_ENCARGADO)
            .tipoPrograma(UPDATED_TIPO_PROGRAMA)
            .modalidadPrograma(UPDATED_MODALIDAD_PROGRAMA)
            .contactosAyuda(UPDATED_CONTACTOS_AYUDA);

        restProgramaAcademicoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedProgramaAcademico.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedProgramaAcademico))
            )
            .andExpect(status().isOk());

        // Validate the ProgramaAcademico in the database
        List<ProgramaAcademico> programaAcademicoList = programaAcademicoRepository.findAll();
        assertThat(programaAcademicoList).hasSize(databaseSizeBeforeUpdate);
        ProgramaAcademico testProgramaAcademico = programaAcademicoList.get(programaAcademicoList.size() - 1);
        assertThat(testProgramaAcademico.getCodigoPrograma()).isEqualTo(UPDATED_CODIGO_PROGRAMA);
        assertThat(testProgramaAcademico.getNombrePrograma()).isEqualTo(UPDATED_NOMBRE_PROGRAMA);
        assertThat(testProgramaAcademico.getCodigoSnies()).isEqualTo(UPDATED_CODIGO_SNIES);
        assertThat(testProgramaAcademico.getNombreEncargado()).isEqualTo(UPDATED_NOMBRE_ENCARGADO);
        assertThat(testProgramaAcademico.getTipoPrograma()).isEqualTo(UPDATED_TIPO_PROGRAMA);
        assertThat(testProgramaAcademico.getModalidadPrograma()).isEqualTo(UPDATED_MODALIDAD_PROGRAMA);
        assertThat(testProgramaAcademico.getContactosAyuda()).isEqualTo(UPDATED_CONTACTOS_AYUDA);
    }

    @Test
    @Transactional
    void putNonExistingProgramaAcademico() throws Exception {
        int databaseSizeBeforeUpdate = programaAcademicoRepository.findAll().size();
        programaAcademico.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProgramaAcademicoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, programaAcademico.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(programaAcademico))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProgramaAcademico in the database
        List<ProgramaAcademico> programaAcademicoList = programaAcademicoRepository.findAll();
        assertThat(programaAcademicoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProgramaAcademico() throws Exception {
        int databaseSizeBeforeUpdate = programaAcademicoRepository.findAll().size();
        programaAcademico.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProgramaAcademicoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(programaAcademico))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProgramaAcademico in the database
        List<ProgramaAcademico> programaAcademicoList = programaAcademicoRepository.findAll();
        assertThat(programaAcademicoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProgramaAcademico() throws Exception {
        int databaseSizeBeforeUpdate = programaAcademicoRepository.findAll().size();
        programaAcademico.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProgramaAcademicoMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(programaAcademico))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProgramaAcademico in the database
        List<ProgramaAcademico> programaAcademicoList = programaAcademicoRepository.findAll();
        assertThat(programaAcademicoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProgramaAcademicoWithPatch() throws Exception {
        // Initialize the database
        programaAcademicoRepository.saveAndFlush(programaAcademico);

        int databaseSizeBeforeUpdate = programaAcademicoRepository.findAll().size();

        // Update the programaAcademico using partial update
        ProgramaAcademico partialUpdatedProgramaAcademico = new ProgramaAcademico();
        partialUpdatedProgramaAcademico.setId(programaAcademico.getId());

        partialUpdatedProgramaAcademico
            .nombreEncargado(UPDATED_NOMBRE_ENCARGADO)
            .tipoPrograma(UPDATED_TIPO_PROGRAMA)
            .modalidadPrograma(UPDATED_MODALIDAD_PROGRAMA)
            .contactosAyuda(UPDATED_CONTACTOS_AYUDA);

        restProgramaAcademicoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProgramaAcademico.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProgramaAcademico))
            )
            .andExpect(status().isOk());

        // Validate the ProgramaAcademico in the database
        List<ProgramaAcademico> programaAcademicoList = programaAcademicoRepository.findAll();
        assertThat(programaAcademicoList).hasSize(databaseSizeBeforeUpdate);
        ProgramaAcademico testProgramaAcademico = programaAcademicoList.get(programaAcademicoList.size() - 1);
        assertThat(testProgramaAcademico.getCodigoPrograma()).isEqualTo(DEFAULT_CODIGO_PROGRAMA);
        assertThat(testProgramaAcademico.getNombrePrograma()).isEqualTo(DEFAULT_NOMBRE_PROGRAMA);
        assertThat(testProgramaAcademico.getCodigoSnies()).isEqualTo(DEFAULT_CODIGO_SNIES);
        assertThat(testProgramaAcademico.getNombreEncargado()).isEqualTo(UPDATED_NOMBRE_ENCARGADO);
        assertThat(testProgramaAcademico.getTipoPrograma()).isEqualTo(UPDATED_TIPO_PROGRAMA);
        assertThat(testProgramaAcademico.getModalidadPrograma()).isEqualTo(UPDATED_MODALIDAD_PROGRAMA);
        assertThat(testProgramaAcademico.getContactosAyuda()).isEqualTo(UPDATED_CONTACTOS_AYUDA);
    }

    @Test
    @Transactional
    void fullUpdateProgramaAcademicoWithPatch() throws Exception {
        // Initialize the database
        programaAcademicoRepository.saveAndFlush(programaAcademico);

        int databaseSizeBeforeUpdate = programaAcademicoRepository.findAll().size();

        // Update the programaAcademico using partial update
        ProgramaAcademico partialUpdatedProgramaAcademico = new ProgramaAcademico();
        partialUpdatedProgramaAcademico.setId(programaAcademico.getId());

        partialUpdatedProgramaAcademico
            .codigoPrograma(UPDATED_CODIGO_PROGRAMA)
            .nombrePrograma(UPDATED_NOMBRE_PROGRAMA)
            .codigoSnies(UPDATED_CODIGO_SNIES)
            .nombreEncargado(UPDATED_NOMBRE_ENCARGADO)
            .tipoPrograma(UPDATED_TIPO_PROGRAMA)
            .modalidadPrograma(UPDATED_MODALIDAD_PROGRAMA)
            .contactosAyuda(UPDATED_CONTACTOS_AYUDA);

        restProgramaAcademicoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProgramaAcademico.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProgramaAcademico))
            )
            .andExpect(status().isOk());

        // Validate the ProgramaAcademico in the database
        List<ProgramaAcademico> programaAcademicoList = programaAcademicoRepository.findAll();
        assertThat(programaAcademicoList).hasSize(databaseSizeBeforeUpdate);
        ProgramaAcademico testProgramaAcademico = programaAcademicoList.get(programaAcademicoList.size() - 1);
        assertThat(testProgramaAcademico.getCodigoPrograma()).isEqualTo(UPDATED_CODIGO_PROGRAMA);
        assertThat(testProgramaAcademico.getNombrePrograma()).isEqualTo(UPDATED_NOMBRE_PROGRAMA);
        assertThat(testProgramaAcademico.getCodigoSnies()).isEqualTo(UPDATED_CODIGO_SNIES);
        assertThat(testProgramaAcademico.getNombreEncargado()).isEqualTo(UPDATED_NOMBRE_ENCARGADO);
        assertThat(testProgramaAcademico.getTipoPrograma()).isEqualTo(UPDATED_TIPO_PROGRAMA);
        assertThat(testProgramaAcademico.getModalidadPrograma()).isEqualTo(UPDATED_MODALIDAD_PROGRAMA);
        assertThat(testProgramaAcademico.getContactosAyuda()).isEqualTo(UPDATED_CONTACTOS_AYUDA);
    }

    @Test
    @Transactional
    void patchNonExistingProgramaAcademico() throws Exception {
        int databaseSizeBeforeUpdate = programaAcademicoRepository.findAll().size();
        programaAcademico.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProgramaAcademicoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, programaAcademico.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(programaAcademico))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProgramaAcademico in the database
        List<ProgramaAcademico> programaAcademicoList = programaAcademicoRepository.findAll();
        assertThat(programaAcademicoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProgramaAcademico() throws Exception {
        int databaseSizeBeforeUpdate = programaAcademicoRepository.findAll().size();
        programaAcademico.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProgramaAcademicoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(programaAcademico))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProgramaAcademico in the database
        List<ProgramaAcademico> programaAcademicoList = programaAcademicoRepository.findAll();
        assertThat(programaAcademicoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProgramaAcademico() throws Exception {
        int databaseSizeBeforeUpdate = programaAcademicoRepository.findAll().size();
        programaAcademico.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProgramaAcademicoMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(programaAcademico))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProgramaAcademico in the database
        List<ProgramaAcademico> programaAcademicoList = programaAcademicoRepository.findAll();
        assertThat(programaAcademicoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProgramaAcademico() throws Exception {
        // Initialize the database
        programaAcademicoRepository.saveAndFlush(programaAcademico);

        int databaseSizeBeforeDelete = programaAcademicoRepository.findAll().size();

        // Delete the programaAcademico
        restProgramaAcademicoMockMvc
            .perform(delete(ENTITY_API_URL_ID, programaAcademico.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProgramaAcademico> programaAcademicoList = programaAcademicoRepository.findAll();
        assertThat(programaAcademicoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
