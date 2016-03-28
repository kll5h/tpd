package com.tilepay.protocol.config;

import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.params.UnitTestParams;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile(value = { "unittest" })
@Configuration("protocolUnitTestConfig")
public class UnitTestConfig extends NetworkParametersConfig {

    @Override
    public NetworkParameters networkParameters() {
        return UnitTestParams.get();
    }

    @Override
    public String getFeeAddress() {
        return "mvQRQAhN2KJia8PgH1xBdmeX3FgTRTdCST";
    }

    @Override
    public Integer getFirstBlockIndex() {
        return null;
    }
}
