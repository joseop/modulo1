package com.udea.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.udea.IntegrationTest;
import com.udea.domain.UnidadAcademica;
import com.udea.repository.UnidadAcademicaRepository;
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
 * Integration tests for the {@link UnidadAcademicaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class UnidadAcademicaResourceIT {

    private static final Integer DEFAULT_CODIGO_UNIDAD = 1;
    private static final Integer UPDATED_CODIGO_UNIDAD = 2;

    private static final String DEFAULT_NOMBRE_UNIDAD = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE_UNIDAD = "BBBBBBBBBB";

    private static final String DEFAULT_TIPO_UNIDAD = "AAAAAAAAAA";
    private static final String UPDATED_TIPO_UNIDAD = "BBBBBBBBBB";

    private static final String DEFAULT_NOMBRE_DECANO = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE_DECANO = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/unidad-academicas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private UnidadAcademicaRepository unidadAcademicaRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUnidadAcademicaMockMvc;

    private UnidadAcademica unidadAcademica;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UnidadAcademica createEntity(EntityManager em) {
        UnidadAcademica unidadAcademica = new UnidadAcademica()
            .codigoUnidad(DEFAULT_CODIGO_UNIDAD)
            .nombreUnidad(DEFAULT_NOMBRE_UNIDAD)
            .tipoUnidad(DEFAULT_TIPO_UNIDAD)
            .nombreDecano(DEFAULT_NOMBRE_DECANO)
            .descripcion(DEFAULT_DESCRIPCION);
        return unidadAcademica;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UnidadAcademica createUpdatedEntity(EntityManager em) {
        UnidadAcademica unidadAcademica = new UnidadAcademica()
            .codigoUnidad(UPDATED_CODIGO_UNIDAD)
            .nombreUnidad(UPDATED_NOMBRE_UNIDAD)
            .tipoUnidad(UPDATED_TIPO_UNIDAD)
            .nombreDecano(UPDATED_NOMBRE_DECANO)
            .descripcion(UPDATED_DESCRIPCION);
        return unidadAcademica;
    }

    @BeforeEach
    public void initTest() {
        unidadAcademica = createEntity(em);
    }

    @Test
    @Transactional
    void createUnidadAcademica() throws Exception {
        int databaseSizeBeforeCreate = unidadAcademicaRepository.findAll().size();
        // Create the UnidadAcademica
        restUnidadAcademicaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(unidadAcademica))
            )
            .andExpect(status().isCreated());

        // Validate the UnidadAcademica in the database
        List<UnidadAcademica> unidadAcademicaList = unidadAcademicaRepository.findAll();
        assertThat(unidadAcademicaList).hasSize(databaseSizeBeforeCreate + 1);
        UnidadAcademica testUnidadAcademica = unidadAcademicaList.get(unidadAcademicaList.size() - 1);
        assertThat(testUnidadAcademica.getCodigoUnidad()).isEqualTo(DEFAULT_CODIGO_UNIDAD);
        assertThat(testUnidadAcademica.getNombreUnidad()).isEqualTo(DEFAULT_NOMBRE_UNIDAD);
        assertThat(testUnidadAcademica.getTipoUnidad()).isEqualTo(DEFAULT_TIPO_UNIDAD);
        assertThat(testUnidadAcademica.getNombreDecano()).isEqualTo(DEFAULT_NOMBRE_DECANO);
        assertThat(testUnidadAcademica.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
    }

    @Test
    @Transactional
    void createUnidadAcademicaWithExistingId() throws Exception {
        // Create the UnidadAcademica with an existing ID
        unidadAcademica.setId(1L);

        int databaseSizeBeforeCreate = unidadAcademicaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restUnidadAcademicaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(unidadAcademica))
            )
            .andExpect(status().isBadRequest());

        // Validate the UnidadAcademica in the database
        List<UnidadAcademica> unidadAcademicaList = unidadAcademicaRepository.findAll();
        assertThat(unidadAcademicaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllUnidadAcademicas() throws Exception {
        // Initialize the database
        unidadAcademicaRepository.saveAndFlush(unidadAcademica);

        // Get all the unidadAcademicaList
        restUnidadAcademicaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(unidadAcademica.getId().intValue())))
            .andExpect(jsonPath("$.[*].codigoUnidad").value(hasItem(DEFAULT_CODIGO_UNIDAD)))
            .andExpect(jsonPath("$.[*].nombreUnidad").value(hasItem(DEFAULT_NOMBRE_UNIDAD)))
            .andExpect(jsonPath("$.[*].tipoUnidad").value(hasItem(DEFAULT_TIPO_UNIDAD)))
            .andExpect(jsonPath("$.[*].nombreDecano").value(hasItem(DEFAULT_NOMBRE_DECANO)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)));
    }

    @Test
    @Transactional
    void getUnidadAcademica() throws Exception {
        // Initialize the database
        unidadAcademicaRepository.saveAndFlush(unidadAcademica);

        // Get the unidadAcademica
        restUnidadAcademicaMockMvc
            .perform(get(ENTITY_API_URL_ID, unidadAcademica.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(unidadAcademica.getId().intValue()))
            .andExpect(jsonPath("$.codigoUnidad").value(DEFAULT_CODIGO_UNIDAD))
            .andExpect(jsonPath("$.nombreUnidad").value(DEFAULT_NOMBRE_UNIDAD))
            .andExpect(jsonPath("$.tipoUnidad").value(DEFAULT_TIPO_UNIDAD))
            .andExpect(jsonPath("$.nombreDecano").value(DEFAULT_NOMBRE_DECANO))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION));
    }

    @Test
    @Transactional
    void getNonExistingUnidadAcademica() throws Exception {
        // Get the unidadAcademica
        restUnidadAcademicaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingUnidadAcademica() throws Exception {
        // Initialize the database
        unidadAcademicaRepository.saveAndFlush(unidadAcademica);

        int databaseSizeBeforeUpdate = unidadAcademicaRepository.findAll().size();

        // Update the unidadAcademica
        UnidadAcademica updatedUnidadAcademica = unidadAcademicaRepository.findById(unidadAcademica.getId()).get();
        // Disconnect from session so that the updates on updatedUnidadAcademica are not directly saved in db
        em.detach(updatedUnidadAcademica);
        updatedUnidadAcademica
            .codigoUnidad(UPDATED_CODIGO_UNIDAD)
            .nombreUnidad(UPDATED_NOMBRE_UNIDAD)
            .tipoUnidad(UPDATED_TIPO_UNIDAD)
            .nombreDecano(UPDATED_NOMBRE_DECANO)
            .descripcion(UPDATED_DESCRIPCION);

        restUnidadAcademicaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedUnidadAcademica.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedUnidadAcademica))
            )
            .andExpect(status().isOk());

        // Validate the UnidadAcademica in the database
        List<UnidadAcademica> unidadAcademicaList = unidadAcademicaRepository.findAll();
        assertThat(unidadAcademicaList).hasSize(databaseSizeBeforeUpdate);
        UnidadAcademica testUnidadAcademica = unidadAcademicaList.get(unidadAcademicaList.size() - 1);
        assertThat(testUnidadAcademica.getCodigoUnidad()).isEqualTo(UPDATED_CODIGO_UNIDAD);
        assertThat(testUnidadAcademica.getNombreUnidad()).isEqualTo(UPDATED_NOMBRE_UNIDAD);
        assertThat(testUnidadAcademica.getTipoUnidad()).isEqualTo(UPDATED_TIPO_UNIDAD);
        assertThat(testUnidadAcademica.getNombreDecano()).isEqualTo(UPDATED_NOMBRE_DECANO);
        assertThat(testUnidadAcademica.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    void putNonExistingUnidadAcademica() throws Exception {
        int databaseSizeBeforeUpdate = unidadAcademicaRepository.findAll().size();
        unidadAcademica.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUnidadAcademicaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, unidadAcademica.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(unidadAcademica))
            )
            .andExpect(status().isBadRequest());

        // Validate the UnidadAcademica in the database
        List<UnidadAcademica> unidadAcademicaList = unidadAcademicaRepository.findAll();
        assertThat(unidadAcademicaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchUnidadAcademica() throws Exception {
        int databaseSizeBeforeUpdate = unidadAcademicaRepository.findAll().size();
        unidadAcademica.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUnidadAcademicaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(unidadAcademica))
            )
            .andExpect(status().isBadRequest());

        // Validate the UnidadAcademica in the database
        List<UnidadAcademica> unidadAcademicaList = unidadAcademicaRepository.findAll();
        assertThat(unidadAcademicaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamUnidadAcademica() throws Exception {
        int databaseSizeBeforeUpdate = unidadAcademicaRepository.findAll().size();
        unidadAcademica.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUnidadAcademicaMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(unidadAcademica))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the UnidadAcademica in the database
        List<UnidadAcademica> unidadAcademicaList = unidadAcademicaRepository.findAll();
        assertThat(unidadAcademicaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateUnidadAcademicaWithPatch() throws Exception {
        // Initialize the database
        unidadAcademicaRepository.saveAndFlush(unidadAcademica);

        int databaseSizeBeforeUpdate = unidadAcademicaRepository.findAll().size();

        // Update the unidadAcademica using partial update
        UnidadAcademica partialUpdatedUnidadAcademica = new UnidadAcademica();
        partialUpdatedUnidadAcademica.setId(unidadAcademica.getId());

        partialUpdatedUnidadAcademica
            .nombreUnidad(UPDATED_NOMBRE_UNIDAD)
            .tipoUnidad(UPDATED_TIPO_UNIDAD)
            .nombreDecano(UPDATED_NOMBRE_DECANO);

        restUnidadAcademicaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUnidadAcademica.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUnidadAcademica))
            )
            .andExpect(status().isOk());

        // Validate the UnidadAcademica in the database
        List<UnidadAcademica> unidadAcademicaList = unidadAcademicaRepository.findAll();
        assertThat(unidadAcademicaList).hasSize(databaseSizeBeforeUpdate);
        UnidadAcademica testUnidadAcademica = unidadAcademicaList.get(unidadAcademicaList.size() - 1);
        assertThat(testUnidadAcademica.getCodigoUnidad()).isEqualTo(DEFAULT_CODIGO_UNIDAD);
        assertThat(testUnidadAcademica.getNombreUnidad()).isEqualTo(UPDATED_NOMBRE_UNIDAD);
        assertThat(testUnidadAcademica.getTipoUnidad()).isEqualTo(UPDATED_TIPO_UNIDAD);
        assertThat(testUnidadAcademica.getNombreDecano()).isEqualTo(UPDATED_NOMBRE_DECANO);
        assertThat(testUnidadAcademica.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
    }

    @Test
    @Transactional
    void fullUpdateUnidadAcademicaWithPatch() throws Exception {
        // Initialize the database
        unidadAcademicaRepository.saveAndFlush(unidadAcademica);

        int databaseSizeBeforeUpdate = unidadAcademicaRepository.findAll().size();

        // Update the unidadAcademica using partial update
        UnidadAcademica partialUpdatedUnidadAcademica = new UnidadAcademica();
        partialUpdatedUnidadAcademica.setId(unidadAcademica.getId());

        partialUpdatedUnidadAcademica
            .codigoUnidad(UPDATED_CODIGO_UNIDAD)
            .nombreUnidad(UPDATED_NOMBRE_UNIDAD)
            .tipoUnidad(UPDATED_TIPO_UNIDAD)
            .nombreDecano(UPDATED_NOMBRE_DECANO)
            .descripcion(UPDATED_DESCRIPCION);

        restUnidadAcademicaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUnidadAcademica.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUnidadAcademica))
            )
            .andExpect(status().isOk());

        // Validate the UnidadAcademica in the database
        List<UnidadAcademica> unidadAcademicaList = unidadAcademicaRepository.findAll();
        assertThat(unidadAcademicaList).hasSize(databaseSizeBeforeUpdate);
        UnidadAcademica testUnidadAcademica = unidadAcademicaList.get(unidadAcademicaList.size() - 1);
        assertThat(testUnidadAcademica.getCodigoUnidad()).isEqualTo(UPDATED_CODIGO_UNIDAD);
        assertThat(testUnidadAcademica.getNombreUnidad()).isEqualTo(UPDATED_NOMBRE_UNIDAD);
        assertThat(testUnidadAcademica.getTipoUnidad()).isEqualTo(UPDATED_TIPO_UNIDAD);
        assertThat(testUnidadAcademica.getNombreDecano()).isEqualTo(UPDATED_NOMBRE_DECANO);
        assertThat(testUnidadAcademica.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    void patchNonExistingUnidadAcademica() throws Exception {
        int databaseSizeBeforeUpdate = unidadAcademicaRepository.findAll().size();
        unidadAcademica.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUnidadAcademicaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, unidadAcademica.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(unidadAcademica))
            )
            .andExpect(status().isBadRequest());

        // Validate the UnidadAcademica in the database
        List<UnidadAcademica> unidadAcademicaList = unidadAcademicaRepository.findAll();
        assertThat(unidadAcademicaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchUnidadAcademica() throws Exception {
        int databaseSizeBeforeUpdate = unidadAcademicaRepository.findAll().size();
        unidadAcademica.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUnidadAcademicaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(unidadAcademica))
            )
            .andExpect(status().isBadRequest());

        // Validate the UnidadAcademica in the database
        List<UnidadAcademica> unidadAcademicaList = unidadAcademicaRepository.findAll();
        assertThat(unidadAcademicaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamUnidadAcademica() throws Exception {
        int databaseSizeBeforeUpdate = unidadAcademicaRepository.findAll().size();
        unidadAcademica.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUnidadAcademicaMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(unidadAcademica))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the UnidadAcademica in the database
        List<UnidadAcademica> unidadAcademicaList = unidadAcademicaRepository.findAll();
        assertThat(unidadAcademicaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteUnidadAcademica() throws Exception {
        // Initialize the database
        unidadAcademicaRepository.saveAndFlush(unidadAcademica);

        int databaseSizeBeforeDelete = unidadAcademicaRepository.findAll().size();

        // Delete the unidadAcademica
        restUnidadAcademicaMockMvc
            .perform(delete(ENTITY_API_URL_ID, unidadAcademica.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UnidadAcademica> unidadAcademicaList = unidadAcademicaRepository.findAll();
        assertThat(unidadAcademicaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
