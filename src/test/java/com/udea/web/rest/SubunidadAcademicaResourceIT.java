package com.udea.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.udea.IntegrationTest;
import com.udea.domain.SubunidadAcademica;
import com.udea.repository.SubunidadAcademicaRepository;
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
 * Integration tests for the {@link SubunidadAcademicaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SubunidadAcademicaResourceIT {

    private static final Integer DEFAULT_CODIGO_SUBUNIDAD = 1;
    private static final Integer UPDATED_CODIGO_SUBUNIDAD = 2;

    private static final String DEFAULT_NOMBRE_SUBUNIDAD = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE_SUBUNIDAD = "BBBBBBBBBB";

    private static final String DEFAULT_TIPO_SUBUNIDAD = "AAAAAAAAAA";
    private static final String UPDATED_TIPO_SUBUNIDAD = "BBBBBBBBBB";

    private static final String DEFAULT_NOMBRE_JEFE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE_JEFE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/subunidad-academicas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SubunidadAcademicaRepository subunidadAcademicaRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSubunidadAcademicaMockMvc;

    private SubunidadAcademica subunidadAcademica;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SubunidadAcademica createEntity(EntityManager em) {
        SubunidadAcademica subunidadAcademica = new SubunidadAcademica()
            .codigoSubunidad(DEFAULT_CODIGO_SUBUNIDAD)
            .nombreSubunidad(DEFAULT_NOMBRE_SUBUNIDAD)
            .tipoSubunidad(DEFAULT_TIPO_SUBUNIDAD)
            .nombreJefe(DEFAULT_NOMBRE_JEFE)
            .descripcion(DEFAULT_DESCRIPCION);
        return subunidadAcademica;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SubunidadAcademica createUpdatedEntity(EntityManager em) {
        SubunidadAcademica subunidadAcademica = new SubunidadAcademica()
            .codigoSubunidad(UPDATED_CODIGO_SUBUNIDAD)
            .nombreSubunidad(UPDATED_NOMBRE_SUBUNIDAD)
            .tipoSubunidad(UPDATED_TIPO_SUBUNIDAD)
            .nombreJefe(UPDATED_NOMBRE_JEFE)
            .descripcion(UPDATED_DESCRIPCION);
        return subunidadAcademica;
    }

    @BeforeEach
    public void initTest() {
        subunidadAcademica = createEntity(em);
    }

    @Test
    @Transactional
    void createSubunidadAcademica() throws Exception {
        int databaseSizeBeforeCreate = subunidadAcademicaRepository.findAll().size();
        // Create the SubunidadAcademica
        restSubunidadAcademicaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(subunidadAcademica))
            )
            .andExpect(status().isCreated());

        // Validate the SubunidadAcademica in the database
        List<SubunidadAcademica> subunidadAcademicaList = subunidadAcademicaRepository.findAll();
        assertThat(subunidadAcademicaList).hasSize(databaseSizeBeforeCreate + 1);
        SubunidadAcademica testSubunidadAcademica = subunidadAcademicaList.get(subunidadAcademicaList.size() - 1);
        assertThat(testSubunidadAcademica.getCodigoSubunidad()).isEqualTo(DEFAULT_CODIGO_SUBUNIDAD);
        assertThat(testSubunidadAcademica.getNombreSubunidad()).isEqualTo(DEFAULT_NOMBRE_SUBUNIDAD);
        assertThat(testSubunidadAcademica.getTipoSubunidad()).isEqualTo(DEFAULT_TIPO_SUBUNIDAD);
        assertThat(testSubunidadAcademica.getNombreJefe()).isEqualTo(DEFAULT_NOMBRE_JEFE);
        assertThat(testSubunidadAcademica.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
    }

    @Test
    @Transactional
    void createSubunidadAcademicaWithExistingId() throws Exception {
        // Create the SubunidadAcademica with an existing ID
        subunidadAcademica.setId(1L);

        int databaseSizeBeforeCreate = subunidadAcademicaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSubunidadAcademicaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(subunidadAcademica))
            )
            .andExpect(status().isBadRequest());

        // Validate the SubunidadAcademica in the database
        List<SubunidadAcademica> subunidadAcademicaList = subunidadAcademicaRepository.findAll();
        assertThat(subunidadAcademicaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSubunidadAcademicas() throws Exception {
        // Initialize the database
        subunidadAcademicaRepository.saveAndFlush(subunidadAcademica);

        // Get all the subunidadAcademicaList
        restSubunidadAcademicaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(subunidadAcademica.getId().intValue())))
            .andExpect(jsonPath("$.[*].codigoSubunidad").value(hasItem(DEFAULT_CODIGO_SUBUNIDAD)))
            .andExpect(jsonPath("$.[*].nombreSubunidad").value(hasItem(DEFAULT_NOMBRE_SUBUNIDAD)))
            .andExpect(jsonPath("$.[*].tipoSubunidad").value(hasItem(DEFAULT_TIPO_SUBUNIDAD)))
            .andExpect(jsonPath("$.[*].nombreJefe").value(hasItem(DEFAULT_NOMBRE_JEFE)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)));
    }

    @Test
    @Transactional
    void getSubunidadAcademica() throws Exception {
        // Initialize the database
        subunidadAcademicaRepository.saveAndFlush(subunidadAcademica);

        // Get the subunidadAcademica
        restSubunidadAcademicaMockMvc
            .perform(get(ENTITY_API_URL_ID, subunidadAcademica.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(subunidadAcademica.getId().intValue()))
            .andExpect(jsonPath("$.codigoSubunidad").value(DEFAULT_CODIGO_SUBUNIDAD))
            .andExpect(jsonPath("$.nombreSubunidad").value(DEFAULT_NOMBRE_SUBUNIDAD))
            .andExpect(jsonPath("$.tipoSubunidad").value(DEFAULT_TIPO_SUBUNIDAD))
            .andExpect(jsonPath("$.nombreJefe").value(DEFAULT_NOMBRE_JEFE))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION));
    }

    @Test
    @Transactional
    void getNonExistingSubunidadAcademica() throws Exception {
        // Get the subunidadAcademica
        restSubunidadAcademicaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSubunidadAcademica() throws Exception {
        // Initialize the database
        subunidadAcademicaRepository.saveAndFlush(subunidadAcademica);

        int databaseSizeBeforeUpdate = subunidadAcademicaRepository.findAll().size();

        // Update the subunidadAcademica
        SubunidadAcademica updatedSubunidadAcademica = subunidadAcademicaRepository.findById(subunidadAcademica.getId()).get();
        // Disconnect from session so that the updates on updatedSubunidadAcademica are not directly saved in db
        em.detach(updatedSubunidadAcademica);
        updatedSubunidadAcademica
            .codigoSubunidad(UPDATED_CODIGO_SUBUNIDAD)
            .nombreSubunidad(UPDATED_NOMBRE_SUBUNIDAD)
            .tipoSubunidad(UPDATED_TIPO_SUBUNIDAD)
            .nombreJefe(UPDATED_NOMBRE_JEFE)
            .descripcion(UPDATED_DESCRIPCION);

        restSubunidadAcademicaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSubunidadAcademica.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedSubunidadAcademica))
            )
            .andExpect(status().isOk());

        // Validate the SubunidadAcademica in the database
        List<SubunidadAcademica> subunidadAcademicaList = subunidadAcademicaRepository.findAll();
        assertThat(subunidadAcademicaList).hasSize(databaseSizeBeforeUpdate);
        SubunidadAcademica testSubunidadAcademica = subunidadAcademicaList.get(subunidadAcademicaList.size() - 1);
        assertThat(testSubunidadAcademica.getCodigoSubunidad()).isEqualTo(UPDATED_CODIGO_SUBUNIDAD);
        assertThat(testSubunidadAcademica.getNombreSubunidad()).isEqualTo(UPDATED_NOMBRE_SUBUNIDAD);
        assertThat(testSubunidadAcademica.getTipoSubunidad()).isEqualTo(UPDATED_TIPO_SUBUNIDAD);
        assertThat(testSubunidadAcademica.getNombreJefe()).isEqualTo(UPDATED_NOMBRE_JEFE);
        assertThat(testSubunidadAcademica.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    void putNonExistingSubunidadAcademica() throws Exception {
        int databaseSizeBeforeUpdate = subunidadAcademicaRepository.findAll().size();
        subunidadAcademica.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSubunidadAcademicaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, subunidadAcademica.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(subunidadAcademica))
            )
            .andExpect(status().isBadRequest());

        // Validate the SubunidadAcademica in the database
        List<SubunidadAcademica> subunidadAcademicaList = subunidadAcademicaRepository.findAll();
        assertThat(subunidadAcademicaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSubunidadAcademica() throws Exception {
        int databaseSizeBeforeUpdate = subunidadAcademicaRepository.findAll().size();
        subunidadAcademica.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubunidadAcademicaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(subunidadAcademica))
            )
            .andExpect(status().isBadRequest());

        // Validate the SubunidadAcademica in the database
        List<SubunidadAcademica> subunidadAcademicaList = subunidadAcademicaRepository.findAll();
        assertThat(subunidadAcademicaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSubunidadAcademica() throws Exception {
        int databaseSizeBeforeUpdate = subunidadAcademicaRepository.findAll().size();
        subunidadAcademica.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubunidadAcademicaMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(subunidadAcademica))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SubunidadAcademica in the database
        List<SubunidadAcademica> subunidadAcademicaList = subunidadAcademicaRepository.findAll();
        assertThat(subunidadAcademicaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSubunidadAcademicaWithPatch() throws Exception {
        // Initialize the database
        subunidadAcademicaRepository.saveAndFlush(subunidadAcademica);

        int databaseSizeBeforeUpdate = subunidadAcademicaRepository.findAll().size();

        // Update the subunidadAcademica using partial update
        SubunidadAcademica partialUpdatedSubunidadAcademica = new SubunidadAcademica();
        partialUpdatedSubunidadAcademica.setId(subunidadAcademica.getId());

        partialUpdatedSubunidadAcademica
            .nombreSubunidad(UPDATED_NOMBRE_SUBUNIDAD)
            .nombreJefe(UPDATED_NOMBRE_JEFE)
            .descripcion(UPDATED_DESCRIPCION);

        restSubunidadAcademicaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSubunidadAcademica.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSubunidadAcademica))
            )
            .andExpect(status().isOk());

        // Validate the SubunidadAcademica in the database
        List<SubunidadAcademica> subunidadAcademicaList = subunidadAcademicaRepository.findAll();
        assertThat(subunidadAcademicaList).hasSize(databaseSizeBeforeUpdate);
        SubunidadAcademica testSubunidadAcademica = subunidadAcademicaList.get(subunidadAcademicaList.size() - 1);
        assertThat(testSubunidadAcademica.getCodigoSubunidad()).isEqualTo(DEFAULT_CODIGO_SUBUNIDAD);
        assertThat(testSubunidadAcademica.getNombreSubunidad()).isEqualTo(UPDATED_NOMBRE_SUBUNIDAD);
        assertThat(testSubunidadAcademica.getTipoSubunidad()).isEqualTo(DEFAULT_TIPO_SUBUNIDAD);
        assertThat(testSubunidadAcademica.getNombreJefe()).isEqualTo(UPDATED_NOMBRE_JEFE);
        assertThat(testSubunidadAcademica.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    void fullUpdateSubunidadAcademicaWithPatch() throws Exception {
        // Initialize the database
        subunidadAcademicaRepository.saveAndFlush(subunidadAcademica);

        int databaseSizeBeforeUpdate = subunidadAcademicaRepository.findAll().size();

        // Update the subunidadAcademica using partial update
        SubunidadAcademica partialUpdatedSubunidadAcademica = new SubunidadAcademica();
        partialUpdatedSubunidadAcademica.setId(subunidadAcademica.getId());

        partialUpdatedSubunidadAcademica
            .codigoSubunidad(UPDATED_CODIGO_SUBUNIDAD)
            .nombreSubunidad(UPDATED_NOMBRE_SUBUNIDAD)
            .tipoSubunidad(UPDATED_TIPO_SUBUNIDAD)
            .nombreJefe(UPDATED_NOMBRE_JEFE)
            .descripcion(UPDATED_DESCRIPCION);

        restSubunidadAcademicaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSubunidadAcademica.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSubunidadAcademica))
            )
            .andExpect(status().isOk());

        // Validate the SubunidadAcademica in the database
        List<SubunidadAcademica> subunidadAcademicaList = subunidadAcademicaRepository.findAll();
        assertThat(subunidadAcademicaList).hasSize(databaseSizeBeforeUpdate);
        SubunidadAcademica testSubunidadAcademica = subunidadAcademicaList.get(subunidadAcademicaList.size() - 1);
        assertThat(testSubunidadAcademica.getCodigoSubunidad()).isEqualTo(UPDATED_CODIGO_SUBUNIDAD);
        assertThat(testSubunidadAcademica.getNombreSubunidad()).isEqualTo(UPDATED_NOMBRE_SUBUNIDAD);
        assertThat(testSubunidadAcademica.getTipoSubunidad()).isEqualTo(UPDATED_TIPO_SUBUNIDAD);
        assertThat(testSubunidadAcademica.getNombreJefe()).isEqualTo(UPDATED_NOMBRE_JEFE);
        assertThat(testSubunidadAcademica.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    void patchNonExistingSubunidadAcademica() throws Exception {
        int databaseSizeBeforeUpdate = subunidadAcademicaRepository.findAll().size();
        subunidadAcademica.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSubunidadAcademicaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, subunidadAcademica.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(subunidadAcademica))
            )
            .andExpect(status().isBadRequest());

        // Validate the SubunidadAcademica in the database
        List<SubunidadAcademica> subunidadAcademicaList = subunidadAcademicaRepository.findAll();
        assertThat(subunidadAcademicaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSubunidadAcademica() throws Exception {
        int databaseSizeBeforeUpdate = subunidadAcademicaRepository.findAll().size();
        subunidadAcademica.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubunidadAcademicaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(subunidadAcademica))
            )
            .andExpect(status().isBadRequest());

        // Validate the SubunidadAcademica in the database
        List<SubunidadAcademica> subunidadAcademicaList = subunidadAcademicaRepository.findAll();
        assertThat(subunidadAcademicaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSubunidadAcademica() throws Exception {
        int databaseSizeBeforeUpdate = subunidadAcademicaRepository.findAll().size();
        subunidadAcademica.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubunidadAcademicaMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(subunidadAcademica))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SubunidadAcademica in the database
        List<SubunidadAcademica> subunidadAcademicaList = subunidadAcademicaRepository.findAll();
        assertThat(subunidadAcademicaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSubunidadAcademica() throws Exception {
        // Initialize the database
        subunidadAcademicaRepository.saveAndFlush(subunidadAcademica);

        int databaseSizeBeforeDelete = subunidadAcademicaRepository.findAll().size();

        // Delete the subunidadAcademica
        restSubunidadAcademicaMockMvc
            .perform(delete(ENTITY_API_URL_ID, subunidadAcademica.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SubunidadAcademica> subunidadAcademicaList = subunidadAcademicaRepository.findAll();
        assertThat(subunidadAcademicaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
