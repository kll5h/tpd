package com.tilepay.daemon.config;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.flywaydb.core.Flyway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FlywayConfig {

    @Inject
    private DataSource dataSource;

    @Bean
    public Flyway flyway() {
        Flyway flyway = new Flyway();
        flyway.setLocations("daemon/db/migration");
        flyway.setDataSource(dataSource);
        flyway.migrate();
        return flyway;
    }

}
