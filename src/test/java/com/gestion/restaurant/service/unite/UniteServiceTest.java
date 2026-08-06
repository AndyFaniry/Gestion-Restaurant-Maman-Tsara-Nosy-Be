package com.gestion.restaurant.service.unite;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class UniteServiceTest {

    private final UniteService service = new UniteService();

    @Test
    void convertir_kgVersG() {
        assertThat(service.convertir("kg", "g", 2.0)).isEqualTo(2000.0);
    }

    @Test
    void convertir_memeUnite() {
        assertThat(service.convertir("ml", "ml", 50.0)).isEqualTo(50.0);
    }

    @Test
    void convertir_symboleNull() {
        assertThatThrownBy(() -> service.convertir(null, "g", 1))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void convertir_nonSupportee() {
        assertThatThrownBy(() -> service.convertir("kg", "xyz", 1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("non supportée");
    }
}
