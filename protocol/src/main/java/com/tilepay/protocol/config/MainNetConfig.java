package com.tilepay.protocol.config;

import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.params.MainNetParams;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile(value = { "mainnet" })
@Configuration("protocolMainNetConfig")
public class MainNetConfig extends NetworkParametersConfig {

    @Override
    public NetworkParameters networkParameters() {
        return MainNetParams.get();
    }

    @Override
    public String getFeeAddress() {
        throw new RuntimeException("Not implemented in mainnet");
    }

    @Override
    public Integer getFirstBlockIndex() {
        throw new RuntimeException("Not implemented in mainnet");
    }
}
