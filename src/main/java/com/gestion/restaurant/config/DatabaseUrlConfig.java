package com.gestion.restaurant.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

/**
 * Accepte {@code DATABASE_URL} au format :
 * <ul>
 *   <li>{@code jdbc:postgresql://...} — local / Docker Compose</li>
 *   <li>{@code postgres://...} ou {@code postgresql://...} — Neon / PaaS (SSL)</li>
 * </ul>
 */
@Configuration
public class DatabaseUrlConfig {

    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource.hikari")
    public DataSource dataSource(
            DataSourceProperties properties,
            @Value("${DATABASE_URL:}") String databaseUrlEnv) {

        String raw = StringUtils.hasText(databaseUrlEnv) ? databaseUrlEnv.trim() : properties.getUrl();
        if (!StringUtils.hasText(raw)) {
            throw new IllegalStateException(
                    "Aucune URL de base de données : définissez DATABASE_URL ou spring.datasource.url");
        }

        HikariDataSource ds = new HikariDataSource();
        ds.setDriverClassName("org.postgresql.Driver");

        if (raw.startsWith("jdbc:")) {
            ds.setJdbcUrl(raw);
            if (StringUtils.hasText(properties.getUsername())) {
                ds.setUsername(properties.getUsername());
            }
            if (StringUtils.hasText(properties.getPassword())) {
                ds.setPassword(properties.getPassword());
            }
            return ds;
        }

        ParsedDatabaseUrl parsed = parseLibPqUrl(raw);
        ds.setJdbcUrl(parsed.jdbcUrl());
        ds.setUsername(parsed.username());
        ds.setPassword(parsed.password());
        return ds;
    }

    static ParsedDatabaseUrl parseLibPqUrl(String databaseUrl) {
        String normalized = databaseUrl;
        if (normalized.startsWith("postgres://")) {
            normalized = "postgresql://" + normalized.substring("postgres://".length());
        }
        if (!normalized.startsWith("postgresql://")) {
            throw new IllegalArgumentException(
                    "DATABASE_URL invalide (attendu jdbc:postgresql://, postgres:// ou postgresql://) : "
                            + mask(databaseUrl));
        }

        URI uri = URI.create(normalized);
        String host = uri.getHost();
        if (host == null || host.isBlank()) {
            throw new IllegalArgumentException("DATABASE_URL sans hôte : " + mask(databaseUrl));
        }
        int port = uri.getPort() > 0 ? uri.getPort() : 5432;
        String path = uri.getPath();
        if (path == null || path.length() <= 1) {
            throw new IllegalArgumentException("DATABASE_URL sans nom de base : " + mask(databaseUrl));
        }
        String database = path.startsWith("/") ? path.substring(1) : path;

        String user = null;
        String password = "";
        if (uri.getUserInfo() != null) {
            String[] parts = uri.getUserInfo().split(":", 2);
            user = decode(parts[0]);
            if (parts.length > 1) {
                password = decode(parts[1]);
            }
        }

        String query = uri.getQuery();
        boolean hasSsl = query != null && query.toLowerCase().contains("sslmode=");
        StringBuilder jdbc = new StringBuilder("jdbc:postgresql://")
                .append(host).append(':').append(port).append('/').append(database);
        if (query != null && !query.isBlank()) {
            jdbc.append('?').append(query);
            if (!hasSsl) {
                jdbc.append("&sslmode=require");
            }
        } else {
            jdbc.append("?sslmode=require");
        }

        return new ParsedDatabaseUrl(jdbc.toString(), user, password);
    }

    private static String decode(String value) {
        return URLDecoder.decode(value, StandardCharsets.UTF_8);
    }

    private static String mask(String url) {
        return url.replaceAll("://([^:/@]+):([^@]+)@", "://$1:***@");
    }

    record ParsedDatabaseUrl(String jdbcUrl, String username, String password) {
    }
}
