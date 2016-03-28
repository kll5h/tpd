package com.tilepay.core.config;

import javax.inject.Inject;

import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.params.UnitTestParams;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.tilepay.core.service.AppDataDirectoryService;

@Profile("unittest")
@Configuration
public class UnitTestParamsConfig implements NetworkParams {

    @Inject
    private AppDataDirectoryService appDataDirectoryService;

    @Override
    public NetworkParameters get() {
        return UnitTestParams.get();
    }

    @Override
    public String getBlockExplorerUrl() {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public String getWalletPrefix() {
        return "unittest";
    }

    @Override
    public String getDbUrl() {
        return "jdbc:h2:file:" + appDataDirectoryService.getDataDirectory() + "/unittest-tilepay";
    }

    @Override
    public String getDbUsername() {
        return "unittest";
    }

    @Override
    public String getDbPassword() {
        return "unittest";
    }

    @Override
    public boolean isShowSql() {
        return true;
    }

    @Override
    public String getNetworkName() {
        return "Unittest";
    }

}
