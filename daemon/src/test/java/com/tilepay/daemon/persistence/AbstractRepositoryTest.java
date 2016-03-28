package com.tilepay.daemon.persistence;

import javax.inject.Inject;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tilepay.daemon.config.DaemonConfig;

@ContextConfiguration(classes = { DaemonConfig.class })
@RunWith(SpringJUnit4ClassRunner.class)
public abstract class AbstractRepositoryTest {
    @Inject protected TransactionRepository transactionRepository;

    @Inject
    protected MessageRepository messageRepository;

    @Inject
    protected AssetRepository assetRepository;

    @Inject
    protected BalanceRepository balanceRepository;

    @Inject
    protected DebitRepository debitRepository;

    @Inject
    protected CreditRepository creditRepository;
}