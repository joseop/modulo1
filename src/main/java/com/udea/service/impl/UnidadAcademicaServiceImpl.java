package com.udea.service.impl;

import com.udea.domain.UnidadAcademica;
import com.udea.repository.UnidadAcademicaRepository;
import com.udea.service.UnidadAcademicaService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link UnidadAcademica}.
 */
@Service
@Transactional
public class UnidadAcademicaServiceImpl implements UnidadAcademicaService {

    private final Logger log = LoggerFactory.getLogger(UnidadAcademicaServiceImpl.class);

    private final UnidadAcademicaRepository unidadAcademicaRepository;

    public UnidadAcademicaServiceImpl(UnidadAcademicaRepository unidadAcademicaRepository) {
        this.unidadAcademicaRepository = unidadAcademicaRepository;
    }

    @Override
    public UnidadAcademica save(UnidadAcademica unidadAcademica) {
        log.debug("Request to save UnidadAcademica : {}", unidadAcademica);
        return unidadAcademicaRepository.save(unidadAcademica);
    }

    @Override
    public UnidadAcademica update(UnidadAcademica unidadAcademica) {
        log.debug("Request to update UnidadAcademica : {}", unidadAcademica);
        return unidadAcademicaRepository.save(unidadAcademica);
    }

    @Override
    public Optional<UnidadAcademica> partialUpdate(UnidadAcademica unidadAcademica) {
        log.debug("Request to partially update UnidadAcademica : {}", unidadAcademica);

        return unidadAcademicaRepository
            .findById(unidadAcademica.getId())
            .map(existingUnidadAcademica -> {
                if (unidadAcademica.getCodigoUnidad() != null) {
                    existingUnidadAcademica.setCodigoUnidad(unidadAcademica.getCodigoUnidad());
                }
                if (unidadAcademica.getNombreUnidad() != null) {
                    existingUnidadAcademica.setNombreUnidad(unidadAcademica.getNombreUnidad());
                }
                if (unidadAcademica.getTipoUnidad() != null) {
                    existingUnidadAcademica.setTipoUnidad(unidadAcademica.getTipoUnidad());
                }
                if (unidadAcademica.getNombreDecano() != null) {
                    existingUnidadAcademica.setNombreDecano(unidadAcademica.getNombreDecano());
                }
                if (unidadAcademica.getDescripcion() != null) {
                    existingUnidadAcademica.setDescripcion(unidadAcademica.getDescripcion());
                }

                return existingUnidadAcademica;
            })
            .map(unidadAcademicaRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UnidadAcademica> findAll(Pageable pageable) {
        log.debug("Request to get all UnidadAcademicas");
        return unidadAcademicaRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UnidadAcademica> findOne(Long id) {
        log.debug("Request to get UnidadAcademica : {}", id);
        return unidadAcademicaRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete UnidadAcademica : {}", id);
        unidadAcademicaRepository.deleteById(id);
    }
}
