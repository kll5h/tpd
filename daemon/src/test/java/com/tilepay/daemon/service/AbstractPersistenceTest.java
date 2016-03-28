package com.tilepay.daemon.service;

import static org.junit.Assert.assertEquals;

import java.math.BigInteger;

import javax.inject.Inject;

import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tilepay.daemon.config.DaemonConfig;
import com.tilepay.daemon.persistence.AssetRepository;
import com.tilepay.daemon.persistence.BalanceRepository;
import com.tilepay.daemon.persistence.BlockRepository;
import com.tilepay.daemon.persistence.CreditRepository;
import com.tilepay.daemon.persistence.DebitRepository;
import com.tilepay.daemon.persistence.MessageRepository;
import com.tilepay.daemon.persistence.TransactionRepository;
import com.tilepay.domain.entity.Asset;
import com.tilepay.domain.entity.Balance;

@ContextConfiguration(classes = { DaemonConfig.class })
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("unittest")
public abstract class AbstractPersistenceTest {

    @Inject
    protected DebitRepository debitRepository;

    @Inject
    protected CreditRepository creditRepository;

    @Inject
    protected AssetRepository assetRepository;

    @Inject
    protected MessageRepository messageRepository;

    @Inject
    protected TransactionRepository transactionRepository;

    @Inject
    protected BalanceRepository balanceRepository;

    @Inject
    protected BlockRepository blockRepository;

    //TODO: 02.01.2015 Andrei Sljusar: move to damain test?
    protected void assertBalance(Balance balance, String address, BigInteger quantity) {
        assertEquals(address, balance.getAddress());
        assertEquals(quantity, balance.getQuantity());
    }

    protected Asset saveAsset(Asset asset) {
        assetRepository.save(asset);
        return asset;
    }
}
