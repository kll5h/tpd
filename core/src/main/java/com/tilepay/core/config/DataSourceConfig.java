package com.tilepay.core.config;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
public class DataSourceConfig {

    @Inject
    private NetworkParams networkParams;

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl(networkParams.getDbUrl());
        dataSource.setUsername(networkParams.getDbUsername());
        dataSource.setPassword(networkParams.getDbPassword());
        return dataSource;
    }

}
