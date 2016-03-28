package com.tilepay.core.service.balance;

import java.math.BigInteger;
import java.util.List;

import org.springframework.stereotype.Service;

import com.tilepay.domain.entity.Asset;
import com.tilepay.domain.entity.Balance;

@Service
public class BitcoinBalanceService extends AbstractBalanceService {

    @Override
    public BigInteger getEstimatedBalance(String address, Asset asset) {
        throw new RuntimeException("Not implemented in Bitcoin");
    }

    @Override
    public List<Balance> getBalances(String address) {
        throw new RuntimeException("Not implemented in Bitcoin");
    }

    @Override
    public void setAssetDivisibility(Asset asset) {
        asset.setDivisible(true);
    }
}
