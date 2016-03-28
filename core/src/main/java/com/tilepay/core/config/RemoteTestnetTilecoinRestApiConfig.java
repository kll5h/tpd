package com.tilepay.core.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile(value = { "remote-testnet" })
public class RemoteTestnetTilecoinRestApiConfig implements TilecoinRestApiConfig {

    @Override
    public String getTilecoinRestApiUrl() {
        return "http://188.166.56.107:8081/";
    }
}
