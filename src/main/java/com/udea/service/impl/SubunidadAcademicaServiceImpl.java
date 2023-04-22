package com.udea.service.impl;

import com.udea.domain.SubunidadAcademica;
import com.udea.repository.SubunidadAcademicaRepository;
import com.udea.service.SubunidadAcademicaService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link SubunidadAcademica}.
 */
@Service
@Transactional
public class SubunidadAcademicaServiceImpl implements SubunidadAcademicaService {

    private final Logger log = LoggerFactory.getLogger(SubunidadAcademicaServiceImpl.class);

    private final SubunidadAcademicaRepository subunidadAcademicaRepository;

    public SubunidadAcademicaServiceImpl(SubunidadAcademicaRepository subunidadAcademicaRepository) {
        this.subunidadAcademicaRepository = subunidadAcademicaRepository;
    }

    @Override
    public SubunidadAcademica save(SubunidadAcademica subunidadAcademica) {
        log.debug("Request to save SubunidadAcademica : {}", subunidadAcademica);
        return subunidadAcademicaRepository.save(subunidadAcademica);
    }

    @Override
    public SubunidadAcademica update(SubunidadAcademica subunidadAcademica) {
        log.debug("Request to update SubunidadAcademica : {}", subunidadAcademica);
        return subunidadAcademicaRepository.save(subunidadAcademica);
    }

    @Override
    public Optional<SubunidadAcademica> partialUpdate(SubunidadAcademica subunidadAcademica) {
        log.debug("Request to partially update SubunidadAcademica : {}", subunidadAcademica);

        return subunidadAcademicaRepository
            .findById(subunidadAcademica.getId())
            .map(existingSubunidadAcademica -> {
                if (subunidadAcademica.getCodigoSubunidad() != null) {
                    existingSubunidadAcademica.setCodigoSubunidad(subunidadAcademica.getCodigoSubunidad());
                }
                if (subunidadAcademica.getNombreSubunidad() != null) {
                    existingSubunidadAcademica.setNombreSubunidad(subunidadAcademica.getNombreSubunidad());
                }
                if (subunidadAcademica.getTipoSubunidad() != null) {
                    existingSubunidadAcademica.setTipoSubunidad(subunidadAcademica.getTipoSubunidad());
                }
                if (subunidadAcademica.getNombreJefe() != null) {
                    existingSubunidadAcademica.setNombreJefe(subunidadAcademica.getNombreJefe());
                }
                if (subunidadAcademica.getDescripcion() != null) {
                    existingSubunidadAcademica.setDescripcion(subunidadAcademica.getDescripcion());
                }

                return existingSubunidadAcademica;
            })
            .map(subunidadAcademicaRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SubunidadAcademica> findAll(Pageable pageable) {
        log.debug("Request to get all SubunidadAcademicas");
        return subunidadAcademicaRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SubunidadAcademica> findOne(Long id) {
        log.debug("Request to get SubunidadAcademica : {}", id);
        return subunidadAcademicaRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete SubunidadAcademica : {}", id);
        subunidadAcademicaRepository.deleteById(id);
    }
}
