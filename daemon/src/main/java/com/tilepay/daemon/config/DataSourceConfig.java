package com.tilepay.daemon.config;

import java.sql.SQLException;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.h2.tools.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
//@PropertySource("classpath:daemon.properties")
public class DataSourceConfig {

    public static final String DB_DRIVER = "db.driver";
    public static final String DB_PASSWORD = "db.password";
    public static final String DB_USERNAME = "db.username";

    @Resource
    private Environment env;

    @Bean
    public DataSource dataSource() throws SQLException {

        String dbTcpPort = env.getRequiredProperty("db.tcp.port");
        String dbName = env.getRequiredProperty("db.name");

        try {
            Server.createTcpServer("-tcpPort", dbTcpPort, "-tcpAllowOthers", "-webAllowOthers", "-pgAllowOthers").start();
        } catch (Exception e) {
            e.printStackTrace();
        }

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getRequiredProperty(DB_DRIVER));
        dataSource.setUrl("jdbc:h2:tcp://localhost:" + dbTcpPort + "/~/" + dbName + ";IFEXISTS=FALSE");
        dataSource.setUsername(env.getRequiredProperty(DB_USERNAME));
        dataSource.setPassword(env.getRequiredProperty(DB_PASSWORD));
        return dataSource;
    }

    @Configuration
    @PropertySource("classpath:daemon-testnet.properties")
    @Profile({ "testnet" })
    static class Testnet {
    }

    @Configuration
    @PropertySource("classpath:daemon-unittest.properties")
    @Profile({ "unittest" })
    static class Unittest {
    }

}
