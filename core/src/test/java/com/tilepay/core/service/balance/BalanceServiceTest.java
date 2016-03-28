package com.tilepay.core.service.balance;

import static com.tilepay.domain.entity.AssetBuilder.anAsset;
import static com.tilepay.domain.entity.BalanceBuilder.aBalance;
import static org.junit.Assert.assertEquals;

import com.tilepay.domain.entity.Asset;
import com.tilepay.domain.entity.Balance;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class BalanceServiceTest {

    private BalanceService balanceService = new BalanceService();

    @Test
    public void getAssets() {

        List<Balance> balances = new ArrayList<>();
        balances.add(aBalance().setAsset(anAsset().setName("OLDCOIN").build()).setQuantity(BigInteger.valueOf(900000)).build());
        balances.add(aBalance().setAsset(anAsset().setName("NEWCOIN").build()).setQuantity(BigInteger.valueOf(100000000)).build());
        balances.add(aBalance().setAsset(anAsset().setName("TILECOINXTC").build()).setQuantity(BigInteger.ZERO).build());
        String btcBalance = "0.00001";

        List<Asset> assets = balanceService.getAssets(balances, btcBalance);
        assertEquals(3, assets.size());

        assertEquals("BTC", assets.get(0).getName());
        assertEquals("OLDCOIN", assets.get(1).getName());
        assertEquals("NEWCOIN", assets.get(2).getName());
    }

}