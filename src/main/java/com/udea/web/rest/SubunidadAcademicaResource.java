package com.udea.web.rest;

import com.udea.domain.SubunidadAcademica;
import com.udea.repository.SubunidadAcademicaRepository;
import com.udea.service.SubunidadAcademicaService;
import com.udea.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.udea.domain.SubunidadAcademica}.
 */
@RestController
@RequestMapping("/api")
public class SubunidadAcademicaResource {

    private final Logger log = LoggerFactory.getLogger(SubunidadAcademicaResource.class);

    private static final String ENTITY_NAME = "subunidadAcademica";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SubunidadAcademicaService subunidadAcademicaService;

    private final SubunidadAcademicaRepository subunidadAcademicaRepository;

    public SubunidadAcademicaResource(
        SubunidadAcademicaService subunidadAcademicaService,
        SubunidadAcademicaRepository subunidadAcademicaRepository
    ) {
        this.subunidadAcademicaService = subunidadAcademicaService;
        this.subunidadAcademicaRepository = subunidadAcademicaRepository;
    }

    /**
     * {@code POST  /subunidad-academicas} : Create a new subunidadAcademica.
     *
     * @param subunidadAcademica the subunidadAcademica to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new subunidadAcademica, or with status {@code 400 (Bad Request)} if the subunidadAcademica has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/subunidad-academicas")
    public ResponseEntity<SubunidadAcademica> createSubunidadAcademica(@RequestBody SubunidadAcademica subunidadAcademica)
        throws URISyntaxException {
        log.debug("REST request to save SubunidadAcademica : {}", subunidadAcademica);
        if (subunidadAcademica.getId() != null) {
            throw new BadRequestAlertException("A new subunidadAcademica cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SubunidadAcademica result = subunidadAcademicaService.save(subunidadAcademica);
        return ResponseEntity
            .created(new URI("/api/subunidad-academicas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /subunidad-academicas/:id} : Updates an existing subunidadAcademica.
     *
     * @param id the id of the subunidadAcademica to save.
     * @param subunidadAcademica the subunidadAcademica to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated subunidadAcademica,
     * or with status {@code 400 (Bad Request)} if the subunidadAcademica is not valid,
     * or with status {@code 500 (Internal Server Error)} if the subunidadAcademica couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/subunidad-academicas/{id}")
    public ResponseEntity<SubunidadAcademica> updateSubunidadAcademica(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SubunidadAcademica subunidadAcademica
    ) throws URISyntaxException {
        log.debug("REST request to update SubunidadAcademica : {}, {}", id, subunidadAcademica);
        if (subunidadAcademica.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, subunidadAcademica.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!subunidadAcademicaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SubunidadAcademica result = subunidadAcademicaService.update(subunidadAcademica);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, subunidadAcademica.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /subunidad-academicas/:id} : Partial updates given fields of an existing subunidadAcademica, field will ignore if it is null
     *
     * @param id the id of the subunidadAcademica to save.
     * @param subunidadAcademica the subunidadAcademica to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated subunidadAcademica,
     * or with status {@code 400 (Bad Request)} if the subunidadAcademica is not valid,
     * or with status {@code 404 (Not Found)} if the subunidadAcademica is not found,
     * or with status {@code 500 (Internal Server Error)} if the subunidadAcademica couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/subunidad-academicas/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SubunidadAcademica> partialUpdateSubunidadAcademica(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SubunidadAcademica subunidadAcademica
    ) throws URISyntaxException {
        log.debug("REST request to partial update SubunidadAcademica partially : {}, {}", id, subunidadAcademica);
        if (subunidadAcademica.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, subunidadAcademica.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!subunidadAcademicaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SubunidadAcademica> result = subunidadAcademicaService.partialUpdate(subunidadAcademica);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, subunidadAcademica.getId().toString())
        );
    }

    /**
     * {@code GET  /subunidad-academicas} : get all the subunidadAcademicas.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of subunidadAcademicas in body.
     */
    @GetMapping("/subunidad-academicas")
    public ResponseEntity<List<SubunidadAcademica>> getAllSubunidadAcademicas(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of SubunidadAcademicas");
        Page<SubunidadAcademica> page = subunidadAcademicaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /subunidad-academicas/:id} : get the "id" subunidadAcademica.
     *
     * @param id the id of the subunidadAcademica to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the subunidadAcademica, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/subunidad-academicas/{id}")
    public ResponseEntity<SubunidadAcademica> getSubunidadAcademica(@PathVariable Long id) {
        log.debug("REST request to get SubunidadAcademica : {}", id);
        Optional<SubunidadAcademica> subunidadAcademica = subunidadAcademicaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(subunidadAcademica);
    }

    /**
     * {@code DELETE  /subunidad-academicas/:id} : delete the "id" subunidadAcademica.
     *
     * @param id the id of the subunidadAcademica to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/subunidad-academicas/{id}")
    public ResponseEntity<Void> deleteSubunidadAcademica(@PathVariable Long id) {
        log.debug("REST request to delete SubunidadAcademica : {}", id);
        subunidadAcademicaService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
