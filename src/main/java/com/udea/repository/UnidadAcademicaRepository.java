package com.udea.repository;

import com.udea.domain.UnidadAcademica;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the UnidadAcademica entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UnidadAcademicaRepository extends JpaRepository<UnidadAcademica, Long> {}
