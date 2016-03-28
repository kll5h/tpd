package com.tilepay.core.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.tilepay.counterpartyclient.config.CounterpartyClientConfig;
import com.tilepay.protocol.config.ProtocolConfig;

@Configuration
@Import(value = { CounterpartyClientConfig.class, ProtocolConfig.class, JpaConfiguration.class, FlywayConfig.class })
@ComponentScan({ "com.tilepay.core" })
public class CoreAppConfig {
}
