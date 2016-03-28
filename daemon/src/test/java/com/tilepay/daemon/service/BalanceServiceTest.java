package com.tilepay.daemon.service;

import static com.tilepay.domain.entity.AssetBuilder.anAsset;
import static com.tilepay.domain.entity.BalanceBuilder.aBalance;
import static org.junit.Assert.assertEquals;

import java.math.BigInteger;
import java.util.List;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.tilepay.daemon.persistence.AssetRepository;
import com.tilepay.daemon.persistence.BalanceRepository;
import com.tilepay.domain.entity.Asset;
import com.tilepay.domain.entity.Balance;
import com.tilepay.domain.entity.Credit;
import com.tilepay.domain.entity.Debit;

public class BalanceServiceTest extends AbstractPersistenceTest {

    @Inject
    private AssetRepository assetRepository;

    @Inject
    private BalanceRepository balanceRepository;

    @Inject
    protected DebitService debitService;

    @Inject
    protected CreditService creditService;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void before() {
        transactionRepository.deleteAllInBatch();
        creditRepository.deleteAllInBatch();
        debitRepository.deleteAllInBatch();
        messageRepository.deleteAllInBatch();
        balanceRepository.deleteAllInBatch();
        assetRepository.deleteAllInBatch();
    }

    @Test
    public void newBalance() {
        Asset asset = assetRepository.save(anAsset().setName("BBBB").setBooked(true).setDivisible(true).build());
        String address = "source";

        creditService.changeBalance(address, asset, new BigInteger("1000"));

        List<Balance> balances = balanceRepository.findByAddressOrderByAssetNameAsc(address);
        assertEquals(1, balances.size());
        Balance balance = balances.get(0);
        assertEquals(1000, balance.getQuantity().intValue());
        assertEquals("BBBB", balance.getAsset().getName());
    }

    @Test
    public void credit() {
        Asset asset = assetRepository.save(anAsset().setName("BBBB").setDivisible(true).setBooked(true).build());
        String address = "address";
        Balance balance = aBalance().setAddress(address).setAsset(asset).setQuantity(12345).build();

        balanceRepository.save(balance);

        creditService.changeBalance(address, asset, new BigInteger("1000"));

        Balance actualBalance = balanceRepository.findByAddressAndAssetName(address, "BBBB");
        assertEquals(13345, actualBalance.getQuantity().intValue());

        List<Credit> credits = creditRepository.findAll();
        assertEquals(1, credits.size());
        assertEquals(new BigInteger("1000"), credits.get(0).getQuantity());
    }

    @Test
    public void debit() {
        Asset asset = assetRepository.save(anAsset().setDivisible(true).setBooked(true).setName("BBBB").build());
        String address = "address";
        Balance balance = aBalance().setAddress(address).setAsset(asset).setQuantity(new BigInteger("12345")).build();

        balanceRepository.save(balance);

        debitService.changeBalance(address, asset, new BigInteger("1000"));

        Balance actualBalance = balanceRepository.findByAddressAndAssetName(address, "BBBB");
        assertEquals(11345, actualBalance.getQuantity().intValue());

        List<Debit> debits = debitRepository.findAll();
        assertEquals(1, debits.size());
        assertEquals(new BigInteger("1000"), debits.get(0).getQuantity());
    }

    @Test
    public void negativeQuantityException() {
        thrown.expect(NegativeQuantityException.class);
        thrown.expectMessage("Quantity: -1 must be positive.");
        creditService.changeBalance(null, null, new BigInteger("-1"));
    }

    @Test
    public void insufficientFundsException() {
        Asset asset = assetRepository.save(anAsset().setName("BBBB").setBooked(true).setDivisible(true).build());
        String address = "source";

        thrown.expect(InsufficientFundsException.class);
        thrown.expectMessage("Can't debit 1000 BBBB from 0 balance. Address: source");
        debitService.changeBalance(address, asset, new BigInteger("1000"));
    }

    @Test
    public void insufficientFundsExceptionOnBalanceUpdate() {
        Asset asset = assetRepository.save(anAsset().setName("BBBB").setBooked(true).setDivisible(true).build());
        String address = "address";
        Balance balance = aBalance().setAddress(address).setAsset(asset).setQuantity(new BigInteger("1000")).build();

        balanceRepository.save(balance);

        thrown.expect(InsufficientFundsException.class);
        thrown.expectMessage("Can't debit 1001. Available balance: " + 1000);
        debitService.changeBalance(address, asset, new BigInteger("1001"));
    }
}