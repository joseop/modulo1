package com.udea.repository;

import com.udea.domain.SubunidadAcademica;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the SubunidadAcademica entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SubunidadAcademicaRepository extends JpaRepository<SubunidadAcademica, Long> {}
