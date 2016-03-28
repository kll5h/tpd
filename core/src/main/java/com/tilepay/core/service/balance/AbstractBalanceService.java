package com.tilepay.core.service.balance;

import java.math.BigInteger;
import java.util.List;

import com.tilepay.domain.entity.Asset;
import com.tilepay.domain.entity.Balance;

public abstract class AbstractBalanceService {

    public abstract BigInteger getEstimatedBalance(String address, Asset asset);

    //TODO: 13.01.2015 Andrei Sljusar: test
    public abstract List<Balance> getBalances(String address);

    public abstract void setAssetDivisibility(Asset asset);
}
