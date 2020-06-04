package com.mycompany.proyecto2.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.proyecto2.web.rest.TestUtil;

public class ExpedienteTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Expediente.class);
        Expediente expediente1 = new Expediente();
        expediente1.setId(1L);
        Expediente expediente2 = new Expediente();
        expediente2.setId(expediente1.getId());
        assertThat(expediente1).isEqualTo(expediente2);
        expediente2.setId(2L);
        assertThat(expediente1).isNotEqualTo(expediente2);
        expediente1.setId(null);
        assertThat(expediente1).isNotEqualTo(expediente2);
    }
}
