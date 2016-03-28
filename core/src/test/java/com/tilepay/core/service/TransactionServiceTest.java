package com.tilepay.core.service;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.bitcoinj.core.Coin;
import org.junit.Test;

public class TransactionServiceTest {

    private TransactionService transactionService = new TransactionService();

    @Test
    public void findInputHash() throws Exception {
        Coin biTarget = transactionService.getBiTarget(new BigDecimal("0.00001"));
        assertEquals(2572, biTarget.value);
    }
}