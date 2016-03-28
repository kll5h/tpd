package com.tilepay.daemon.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.tilepay.counterpartyclient.config.CounterpartyClientConfig;
import com.tilepay.protocol.config.ProtocolConfig;

@Configuration
@EnableAsync
@EnableScheduling
@Import(value = { ProtocolConfig.class, CounterpartyClientConfig.class })
@ComponentScan(basePackages = "com.tilepay.daemon")
public class DaemonConfig {
}
