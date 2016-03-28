package com.tilepay.protocol.config;

import org.bitcoinj.core.NetworkParameters;

public abstract class NetworkParametersConfig {

    public abstract NetworkParameters networkParameters();

    public abstract String getFeeAddress();

    public abstract Integer getFirstBlockIndex();
}
