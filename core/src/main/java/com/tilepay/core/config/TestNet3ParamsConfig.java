package com.tilepay.core.config;

import javax.inject.Inject;

import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.params.TestNet3Params;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.tilepay.core.service.AppDataDirectoryService;

@Profile(value = { "default", "testnet" })
@Configuration
public class TestNet3ParamsConfig implements NetworkParams {

    @Inject
    private AppDataDirectoryService appDataDirectoryService;

    @Override
    public NetworkParameters get() {
        return TestNet3Params.get();
    }

    @Override
    public String getBlockExplorerUrl() {
        return "https://www.blocktrail.com/tBTC";
    }

    @Override
    public String getWalletPrefix() {
        return "testnet";
    }

    @Override
    public String getDbUrl() {
        return "jdbc:h2:file:" + appDataDirectoryService.getDataDirectory() + "testnet-tilepay";
    }

    @Override
    public String getDbUsername() {
        return "tilepay";
    }

    @Override
    public String getDbPassword() {
        return "tilepay";
    }

    @Override
    public boolean isShowSql() {
        return false;
    }

    @Override
    public String getNetworkName() {
        return "Testnet";
    }


}
