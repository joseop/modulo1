package com.udea.service;

import com.udea.domain.SubunidadAcademica;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link SubunidadAcademica}.
 */
public interface SubunidadAcademicaService {
    /**
     * Save a subunidadAcademica.
     *
     * @param subunidadAcademica the entity to save.
     * @return the persisted entity.
     */
    SubunidadAcademica save(SubunidadAcademica subunidadAcademica);

    /**
     * Updates a subunidadAcademica.
     *
     * @param subunidadAcademica the entity to update.
     * @return the persisted entity.
     */
    SubunidadAcademica update(SubunidadAcademica subunidadAcademica);

    /**
     * Partially updates a subunidadAcademica.
     *
     * @param subunidadAcademica the entity to update partially.
     * @return the persisted entity.
     */
    Optional<SubunidadAcademica> partialUpdate(SubunidadAcademica subunidadAcademica);

    /**
     * Get all the subunidadAcademicas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SubunidadAcademica> findAll(Pageable pageable);

    /**
     * Get the "id" subunidadAcademica.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SubunidadAcademica> findOne(Long id);

    /**
     * Delete the "id" subunidadAcademica.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
