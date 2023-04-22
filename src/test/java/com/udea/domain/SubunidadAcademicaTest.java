package com.udea.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.udea.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SubunidadAcademicaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SubunidadAcademica.class);
        SubunidadAcademica subunidadAcademica1 = new SubunidadAcademica();
        subunidadAcademica1.setId(1L);
        SubunidadAcademica subunidadAcademica2 = new SubunidadAcademica();
        subunidadAcademica2.setId(subunidadAcademica1.getId());
        assertThat(subunidadAcademica1).isEqualTo(subunidadAcademica2);
        subunidadAcademica2.setId(2L);
        assertThat(subunidadAcademica1).isNotEqualTo(subunidadAcademica2);
        subunidadAcademica1.setId(null);
        assertThat(subunidadAcademica1).isNotEqualTo(subunidadAcademica2);
    }
}
