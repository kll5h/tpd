package com.tilepay.core.service;

import static com.tilepay.domain.entity.BalanceBuilder.aBalance;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import com.tilepay.counterpartyclient.model.CounterpartyBalance;
import com.tilepay.domain.entity.Asset;
import com.tilepay.domain.entity.Balance;

@Component
public class BalanceConverter {

    @Inject
    private AssetConverter assetConverter;

    public Balance getBalance(CounterpartyBalance counterpartyBalance) {
        Asset asset = assetConverter.getAsset(counterpartyBalance.getAsset());
        return aBalance().setAsset(asset).setQuantity(counterpartyBalance.getQuantityAsBigInteger()).setEstimatedQuantity(counterpartyBalance.getEstimatedQuantity()).build();
    }

    public List<Balance> getBalances(List<CounterpartyBalance> counterpartyBalances) {

        List<Balance> balances = new ArrayList<>();

        for (CounterpartyBalance counterpartyBalance : counterpartyBalances) {
            Balance balance = getBalance(counterpartyBalance);
            balances.add(balance);
        }

        return balances;
    }

}
