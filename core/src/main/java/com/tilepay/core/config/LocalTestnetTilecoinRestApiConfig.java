package com.tilepay.core.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile(value = { "unittest", "local-testnet" })
public class LocalTestnetTilecoinRestApiConfig implements TilecoinRestApiConfig {

    @Override
    public String getTilecoinRestApiUrl() {
        return "http://localhost:8081/";
    }
}
