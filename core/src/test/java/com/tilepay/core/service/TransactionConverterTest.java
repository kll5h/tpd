package com.tilepay.core.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigInteger;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tilepay.core.config.CoreAppConfig;
import com.tilepay.core.dto.AssetIssuanceDto;
import com.tilepay.domain.entity.Asset;
import com.tilepay.domain.entity.Issuance;
import com.tilepay.domain.entity.Transaction;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = CoreAppConfig.class)
@ActiveProfiles("unittest")
public class TransactionConverterTest {

    @Inject
    private TransactionConverter transactionConverter;

    @Test
    public void getIssuanceTransaction() {
        AssetIssuanceDto form = new AssetIssuanceDto();
        form.setSource("source");
        form.setDivisible(true);
        form.setQuantity("10000");
        form.setAssetName("QQQQ");
        Transaction transaction = transactionConverter.getIssuanceTransaction(form);

        assertEquals("source", transaction.getSource());
        assertEquals("source", transaction.getDestination());

        Issuance issuance = (Issuance) transaction.getMessage();
        assertEquals(new BigInteger("1000000000000"), issuance.getQuantity());

        Asset asset = issuance.getAsset();
        assertEquals("QQQQ", asset.getName());
        assertEquals(true, asset.getDivisible());
        assertTrue(asset.isTilecoin());
    }
}