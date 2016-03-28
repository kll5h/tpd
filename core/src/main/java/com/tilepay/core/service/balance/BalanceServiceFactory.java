package com.tilepay.core.service.balance;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import com.tilepay.domain.entity.Asset;

@Component
public class BalanceServiceFactory {

    @Inject
    private BitcoinBalanceService bitcoinBalanceService;

    @Inject
    private TilecoinBalanceService tilecoinBalanceService;

    @Inject
    private CounterpartyBalanceService counterpartyBalanceService;

    public AbstractBalanceService getBalanceService(Asset asset) {

        if (asset.isCntrprty()) {
            return counterpartyBalanceService;
        } else if (asset.isTilecoin()) {
            return tilecoinBalanceService;
        } else if (asset.isBitcoin()) {
            return bitcoinBalanceService;
        }
        throw new IllegalArgumentException("Unknown/Unsupported protocol: " + asset.getProtocol() + ", asset name: " + asset.getName());
    }

    public TilecoinBalanceService getTilecoinBalanceService() {
        return tilecoinBalanceService;
    }

    public CounterpartyBalanceService getCounterpartyBalanceService() {
        return counterpartyBalanceService;
    }
}
