package com.tilepay.web.controller;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tilepay.core.service.WalletAppKitHolder;

@RestController
public class BlockchainController {

    @Inject
    private WalletAppKitHolder walletAppKitHolder;

    @RequestMapping(value = "/checkBlockchain", method = RequestMethod.GET)
    public boolean isBlockchainDownloading() {
        return walletAppKitHolder.isStarting();
    }
}
