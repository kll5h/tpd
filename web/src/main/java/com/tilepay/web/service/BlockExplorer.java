package com.tilepay.web.service;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import com.tilepay.core.config.NetworkParams;

@Component
public class BlockExplorer {

    @Inject
    private NetworkParams networkParams;

    public String getTransactionUrl(String txHash) {
        return networkParams.getBlockExplorerUrl() + "/tx/" + txHash;
    }

    public String getAddressUrl(String address) {
        return networkParams.getBlockExplorerUrl() + "/address/" + address;
    }

}
