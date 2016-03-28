package com.tilepay.daemon.service;

import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.tilepay.daemon.persistence.AssetRepository;
import com.tilepay.daemon.persistence.BalanceRepository;
import com.tilepay.domain.entity.Asset;
import com.tilepay.domain.entity.Balance;
import com.tilepay.domain.entity.Protocol;

@Service
public class DaemonBalanceService {

    @Inject
    private BalanceRepository balanceRepository;
    
    @Inject
    private AssetRepository assetRepository;

    //TODO: 28.01.2015 Andrei Sljusar: test
    public List<Balance> getBalances(String address) {
        List<Balance> balances = balanceRepository.findByAddressOrderByAssetNameAsc(address);

        Iterator<Balance> iterator = balances.iterator();

        while (iterator.hasNext()) {
            Balance balance = iterator.next();
            Asset asset = balance.getAsset();
            if (asset.getName().equals(Asset.CNTRPRTY_TILECOINX)) {
                iterator.remove();
                continue;
            }
            asset.setProtocol(Protocol.TILECOIN.name());
        }
        return balances;
    }
    
    public void saveBalance(Balance balance) {
        Asset currentAsset = assetRepository.findByName(balance.getAsset().getName());
        if (currentAsset == null) {
        	balance.getAsset().setBooked(true);
        	assetRepository.save(balance.getAsset());
        	balanceRepository.save(balance);
        }
        //TODO: 30.06.2015 xuht: exists and taken/not taken?
    }
}
