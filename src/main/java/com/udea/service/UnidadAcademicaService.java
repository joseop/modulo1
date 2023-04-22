package com.udea.service;

import com.udea.domain.UnidadAcademica;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link UnidadAcademica}.
 */
public interface UnidadAcademicaService {
    /**
     * Save a unidadAcademica.
     *
     * @param unidadAcademica the entity to save.
     * @return the persisted entity.
     */
    UnidadAcademica save(UnidadAcademica unidadAcademica);

    /**
     * Updates a unidadAcademica.
     *
     * @param unidadAcademica the entity to update.
     * @return the persisted entity.
     */
    UnidadAcademica update(UnidadAcademica unidadAcademica);

    /**
     * Partially updates a unidadAcademica.
     *
     * @param unidadAcademica the entity to update partially.
     * @return the persisted entity.
     */
    Optional<UnidadAcademica> partialUpdate(UnidadAcademica unidadAcademica);

    /**
     * Get all the unidadAcademicas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<UnidadAcademica> findAll(Pageable pageable);

    /**
     * Get the "id" unidadAcademica.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<UnidadAcademica> findOne(Long id);

    /**
     * Delete the "id" unidadAcademica.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
