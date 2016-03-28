package com.tilepay.core.config;

import org.bitcoinj.core.NetworkParameters;

public interface NetworkParams {
    NetworkParameters get();

    String getBlockExplorerUrl();

    String getWalletPrefix();

    String getDbUrl();

    String getDbUsername();

    String getDbPassword();

    boolean isShowSql();

    String getNetworkName();
}
