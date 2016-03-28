package com.tilepay.core.config;

import javax.inject.Inject;

import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.params.MainNetParams;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.tilepay.core.service.AppDataDirectoryService;

@Profile(value = { "mainnet" })
@Configuration
public class MainNetParamsConfig implements NetworkParams {

    @Inject
    private AppDataDirectoryService appDataDirectoryService;

    @Override
    public NetworkParameters get() {
        return MainNetParams.get();
    }

    @Override
    public String getBlockExplorerUrl() {
        return "https://www.blocktrail.com/BTC/";
    }

    @Override
    public String getWalletPrefix() {
        return "mainnet";
    }

    @Override
    public String getDbUrl() {
        return "jdbc:h2:file:" + appDataDirectoryService.getDataDirectory() + "mainnet-tilepay";
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
        return "Mainnet";
    }

}
