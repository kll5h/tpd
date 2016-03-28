package com.tilepay.daemon.persistence;

import static com.tilepay.domain.entity.AssetBuilder.anAsset;
import static com.tilepay.domain.entity.BalanceBuilder.aBalance;
import static org.junit.Assert.assertEquals;

import java.math.BigInteger;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.tilepay.domain.entity.Asset;
import com.tilepay.domain.entity.Balance;

public class BalanceRepositoryTest extends AbstractRepositoryTest {

    @Before
    public void before() {
        debitRepository.deleteAllInBatch();
        creditRepository.deleteAllInBatch();
        transactionRepository.deleteAllInBatch();
        messageRepository.deleteAllInBatch();
        balanceRepository.deleteAllInBatch();
        assetRepository.deleteAllInBatch();
    }

    @Test
    public void save() {
        Asset asset = saveAsset(anAsset().setAssetId("12345").setDivisible(true).setBooked(true).setName("ABCDE").build());

        Balance balance = aBalance().setAddress("address").setAsset(asset).setQuantity(BigInteger.valueOf(12345L)).build();

        balanceRepository.save(balance);

        balance = balanceRepository.findOne(balance.getId());
        assertEquals("address", balance.getAddress());
        assertEquals("ABCDE", balance.getAsset().getName());
    }

    @Test
    public void findByAddressOrderByAssetNameAsc() {
        Asset asset = saveAsset(anAsset().setName("B").setDivisible(true).setBooked(true).build());
        balanceRepository.save(aBalance().setAsset(asset).setAddress("address").setQuantity(BigInteger.valueOf(12345L)).build());

        asset = saveAsset(anAsset().setName("A").setDivisible(true).setBooked(true).build());
        balanceRepository.save(aBalance().setAsset(asset).setAddress("address").setQuantity(BigInteger.valueOf(12345L)).build());

        List<Balance> balances = balanceRepository.findByAddressOrderByAssetNameAsc("address");
        assertEquals(12345L, balances.get(0).getQuantity().longValue());
        assertEquals("A", balances.get(0).getAsset().getName());
    }

    @Test
    public void findByAddressAndAssetName() {
        Asset asset = saveAsset(anAsset().setAssetId("12345").setDivisible(true).setBooked(true).setName("ABCDE").build());

        Balance balance = aBalance().setAddress("address").setAsset(asset).setQuantity(BigInteger.valueOf(12345L)).build();
        balanceRepository.save(balance);

        Balance actualBalance = balanceRepository.findByAddressAndAssetName("address", "ABCDE");
        assertEquals(12345L, actualBalance.getQuantity().longValue());
    }

    private Asset saveAsset(Asset asset) {
        assetRepository.save(asset);
        return asset;
    }

}