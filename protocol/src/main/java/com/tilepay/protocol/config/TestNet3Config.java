package com.tilepay.protocol.config;

import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.params.TestNet3Params;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile(value = { "default", "testnet" })
@Configuration
public class TestNet3Config extends NetworkParametersConfig {

    @Override
    public NetworkParameters networkParameters() {
        return TestNet3Params.get();
    }

    @Override
    public String getFeeAddress() {
        return "mvQRQAhN2KJia8PgH1xBdmeX3FgTRTdCST";
    }

    @Override
    public Integer getFirstBlockIndex() {
        return 319328;
    }
}
