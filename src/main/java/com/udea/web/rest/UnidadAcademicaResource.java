package com.udea.web.rest;

import com.udea.domain.UnidadAcademica;
import com.udea.repository.UnidadAcademicaRepository;
import com.udea.service.UnidadAcademicaService;
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
 * REST controller for managing {@link com.udea.domain.UnidadAcademica}.
 */
@RestController
@RequestMapping("/api")
public class UnidadAcademicaResource {

    private final Logger log = LoggerFactory.getLogger(UnidadAcademicaResource.class);

    private static final String ENTITY_NAME = "unidadAcademica";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UnidadAcademicaService unidadAcademicaService;

    private final UnidadAcademicaRepository unidadAcademicaRepository;

    public UnidadAcademicaResource(UnidadAcademicaService unidadAcademicaService, UnidadAcademicaRepository unidadAcademicaRepository) {
        this.unidadAcademicaService = unidadAcademicaService;
        this.unidadAcademicaRepository = unidadAcademicaRepository;
    }

    /**
     * {@code POST  /unidad-academicas} : Create a new unidadAcademica.
     *
     * @param unidadAcademica the unidadAcademica to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new unidadAcademica, or with status {@code 400 (Bad Request)} if the unidadAcademica has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/unidad-academicas")
    public ResponseEntity<UnidadAcademica> createUnidadAcademica(@RequestBody UnidadAcademica unidadAcademica) throws URISyntaxException {
        log.debug("REST request to save UnidadAcademica : {}", unidadAcademica);
        if (unidadAcademica.getId() != null) {
            throw new BadRequestAlertException("A new unidadAcademica cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UnidadAcademica result = unidadAcademicaService.save(unidadAcademica);
        return ResponseEntity
            .created(new URI("/api/unidad-academicas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /unidad-academicas/:id} : Updates an existing unidadAcademica.
     *
     * @param id the id of the unidadAcademica to save.
     * @param unidadAcademica the unidadAcademica to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated unidadAcademica,
     * or with status {@code 400 (Bad Request)} if the unidadAcademica is not valid,
     * or with status {@code 500 (Internal Server Error)} if the unidadAcademica couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/unidad-academicas/{id}")
    public ResponseEntity<UnidadAcademica> updateUnidadAcademica(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody UnidadAcademica unidadAcademica
    ) throws URISyntaxException {
        log.debug("REST request to update UnidadAcademica : {}, {}", id, unidadAcademica);
        if (unidadAcademica.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, unidadAcademica.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!unidadAcademicaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        UnidadAcademica result = unidadAcademicaService.update(unidadAcademica);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, unidadAcademica.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /unidad-academicas/:id} : Partial updates given fields of an existing unidadAcademica, field will ignore if it is null
     *
     * @param id the id of the unidadAcademica to save.
     * @param unidadAcademica the unidadAcademica to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated unidadAcademica,
     * or with status {@code 400 (Bad Request)} if the unidadAcademica is not valid,
     * or with status {@code 404 (Not Found)} if the unidadAcademica is not found,
     * or with status {@code 500 (Internal Server Error)} if the unidadAcademica couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/unidad-academicas/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<UnidadAcademica> partialUpdateUnidadAcademica(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody UnidadAcademica unidadAcademica
    ) throws URISyntaxException {
        log.debug("REST request to partial update UnidadAcademica partially : {}, {}", id, unidadAcademica);
        if (unidadAcademica.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, unidadAcademica.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!unidadAcademicaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<UnidadAcademica> result = unidadAcademicaService.partialUpdate(unidadAcademica);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, unidadAcademica.getId().toString())
        );
    }

    /**
     * {@code GET  /unidad-academicas} : get all the unidadAcademicas.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of unidadAcademicas in body.
     */
    @GetMapping("/unidad-academicas")
    public ResponseEntity<List<UnidadAcademica>> getAllUnidadAcademicas(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of UnidadAcademicas");
        Page<UnidadAcademica> page = unidadAcademicaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /unidad-academicas/:id} : get the "id" unidadAcademica.
     *
     * @param id the id of the unidadAcademica to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the unidadAcademica, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/unidad-academicas/{id}")
    public ResponseEntity<UnidadAcademica> getUnidadAcademica(@PathVariable Long id) {
        log.debug("REST request to get UnidadAcademica : {}", id);
        Optional<UnidadAcademica> unidadAcademica = unidadAcademicaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(unidadAcademica);
    }

    /**
     * {@code DELETE  /unidad-academicas/:id} : delete the "id" unidadAcademica.
     *
     * @param id the id of the unidadAcademica to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/unidad-academicas/{id}")
    public ResponseEntity<Void> deleteUnidadAcademica(@PathVariable Long id) {
        log.debug("REST request to delete UnidadAcademica : {}", id);
        unidadAcademicaService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
