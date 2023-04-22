package com.udea.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.udea.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class UnidadAcademicaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UnidadAcademica.class);
        UnidadAcademica unidadAcademica1 = new UnidadAcademica();
        unidadAcademica1.setId(1L);
        UnidadAcademica unidadAcademica2 = new UnidadAcademica();
        unidadAcademica2.setId(unidadAcademica1.getId());
        assertThat(unidadAcademica1).isEqualTo(unidadAcademica2);
        unidadAcademica2.setId(2L);
        assertThat(unidadAcademica1).isNotEqualTo(unidadAcademica2);
        unidadAcademica1.setId(null);
        assertThat(unidadAcademica1).isNotEqualTo(unidadAcademica2);
    }
}
