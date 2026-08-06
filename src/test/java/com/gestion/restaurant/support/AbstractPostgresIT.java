package com.gestion.restaurant.support;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.DockerClientFactory;
import org.testcontainers.containers.PostgreSQLContainer;

/**
 * Base des tests d'intégration / fonctionnels.
 * <p>
 * Priorité : Testcontainers (Postgres 16) si Docker est disponible ;
 * sinon {@code TEST_DATABASE_URL} ou Postgres local {@code restaurant_test}.
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public abstract class AbstractPostgresIT {

    private static final PostgreSQLContainer<?> POSTGRES;
    private static final String FALLBACK_URL;

    static {
        PostgreSQLContainer<?> container = null;
        if (isDockerAvailable()) {
            try {
                container = new PostgreSQLContainer<>("postgres:16-alpine")
                        .withDatabaseName("restaurant_test")
                        .withUsername("test")
                        .withPassword("test");
                container.start();
            } catch (Exception ex) {
                System.err.println("[AbstractPostgresIT] Testcontainers indisponible: " + ex.getMessage());
                container = null;
            }
        }
        POSTGRES = container;
        FALLBACK_URL = System.getenv().getOrDefault(
                "TEST_DATABASE_URL",
                "jdbc:postgresql://127.0.0.1:5432/restaurant_test?user=dev1&password=dev"
        );
    }

    private static boolean isDockerAvailable() {
        try {
            return DockerClientFactory.instance().isDockerAvailable();
        } catch (Throwable ex) {
            return false;
        }
    }

    @DynamicPropertySource
    static void registerDatasource(DynamicPropertyRegistry registry) {
        if (POSTGRES != null && POSTGRES.isRunning()) {
            registry.add("DATABASE_URL", () -> String.format(
                    "jdbc:postgresql://%s:%d/%s?user=%s&password=%s",
                    POSTGRES.getHost(),
                    POSTGRES.getFirstMappedPort(),
                    POSTGRES.getDatabaseName(),
                    POSTGRES.getUsername(),
                    POSTGRES.getPassword()
            ));
        } else {
            registry.add("DATABASE_URL", () -> FALLBACK_URL);
        }
    }
}
