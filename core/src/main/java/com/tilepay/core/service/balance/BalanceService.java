package com.tilepay.core.service.balance;

import static com.tilepay.domain.entity.AssetBuilder.anAsset;

import com.tilepay.domain.entity.Asset;
import com.tilepay.domain.entity.Balance;
import com.tilepay.domain.entity.CurrencyEnum;
import com.tilepay.domain.entity.Protocol;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import org.springframework.stereotype.Service;

@Service
public class BalanceService {

    @Inject
    private BalanceServiceFactory balanceServiceFactory;

    //TODO: 13.01.2015 Andrei Sljusar: test
    public List<Balance> getBalances(String address) {
        List<Balance> allBalances = new ArrayList<>();
        
        CounterpartyBalanceService counterpartyBalanceService = balanceServiceFactory.getCounterpartyBalanceService();
        List<Balance> counterpartyBalances = counterpartyBalanceService.getBalances(address);
        allBalances.addAll(counterpartyBalances);
        
        TilecoinBalanceService tilecoinBalanceService = balanceServiceFactory.getTilecoinBalanceService();
        List<Balance> tilecoinBalances = tilecoinBalanceService.getBalances(address);
        allBalances.addAll(tilecoinBalances);
        
        return allBalances;
    }

    public List<Asset> getAssets(List<Balance> balances, String btcBalance) {
        List<Asset> availableCurrencies = new ArrayList<>();

        balances.stream().filter(balance -> balance.getQuantity().compareTo(BigInteger.ZERO) > 0).forEach(balance -> {
            Asset asset = balance.getAsset();
            asset.setMinersFee(Asset.CUSTOM_CURRENCIES_MINERS_FEE);
            // TODO: 03.01.2015 Andrei Sljusar: check also protocol...
            //TODO: 13.01.2015 Andrei Sljusar: test
            if (asset.getName().equalsIgnoreCase(CurrencyEnum.TILECOINXTC.name())) {
                availableCurrencies.add(0, asset);
            } else {
                availableCurrencies.add(asset);
            }
        });

        if (Double.parseDouble(btcBalance) > 0) {
            Asset btcCurrency = anAsset().setName(CurrencyEnum.BTC.name()).setMinersFee(Asset.BTC_MINERS_FEE).setProtocol(Protocol.BITCOIN).build();
            availableCurrencies.add(0, btcCurrency);
        }

        return availableCurrencies;
    }
}
