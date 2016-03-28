package com.tilepay.core.service;

import static com.tilepay.counterpartyclient.model.CounterpartyBalanceBuilder.aCounterpartyBalance;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.math.BigInteger;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.tilepay.counterpartyclient.model.CounterpartyBalance;
import com.tilepay.domain.entity.AssetBuilder;
import com.tilepay.domain.entity.Balance;

@RunWith(MockitoJUnitRunner.class)
public class BalanceConverterTest {

    @Mock
    private AssetConverter assetConverter;

    @InjectMocks
    private BalanceConverter balanceConverter;

    @Test
    public void getBalance() throws Exception {

        CounterpartyBalance counterpartyBalance = aCounterpartyBalance().setQuantity(new BigInteger("100000")).setEstimatedQuantity(200000).setAsset("AAAA").build();

        when(assetConverter.getAsset("AAAA")).thenReturn(AssetBuilder.anAsset().setName("AAAA").build());

        Balance balance = balanceConverter.getBalance(counterpartyBalance);

        assertEquals("AAAA", balance.getAsset().getName());
        assertEquals(new BigInteger("100000"), balance.getQuantity());
        assertEquals(200000, balance.getEstimatedQuantity().longValue());

    }
}