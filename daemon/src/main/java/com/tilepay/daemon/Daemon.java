package com.tilepay.daemon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.tilepay.daemon.config.DaemonConfig;

@Configuration
@Import(DaemonConfig.class)
@EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class })
public class Daemon {

    public static void main(String[] args) {
        SpringApplication.run(Daemon.class);
    }

}