package com.gestion.restaurant.config;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DatabaseUrlConfigTest {

    @Test
    void parseNeonUrl_ajouteSslEtJdbc() {
        var parsed = DatabaseUrlConfig.parseLibPqUrl(
                "postgresql://neondb_owner:s3cret@ep-demo.eu-central-1.aws.neon.tech/neondb");

        assertThat(parsed.jdbcUrl()).isEqualTo(
                "jdbc:postgresql://ep-demo.eu-central-1.aws.neon.tech:5432/neondb?sslmode=require");
        assertThat(parsed.username()).isEqualTo("neondb_owner");
        assertThat(parsed.password()).isEqualTo("s3cret");
    }

    @Test
    void parseNeonUrl_conserveSslmodeExistant() {
        var parsed = DatabaseUrlConfig.parseLibPqUrl(
                "postgres://u:p@ep-x.neon.tech/db?sslmode=require&channel_binding=require");

        assertThat(parsed.jdbcUrl()).startsWith("jdbc:postgresql://ep-x.neon.tech:5432/db?");
        assertThat(parsed.jdbcUrl()).contains("sslmode=require");
        assertThat(parsed.jdbcUrl()).doesNotContain("sslmode=require&sslmode");
    }

    @Test
    void parse_invalide() {
        assertThatThrownBy(() -> DatabaseUrlConfig.parseLibPqUrl("mysql://localhost/db"))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
