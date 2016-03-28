package com.tilepay.core.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tilepay.core.config.CoreAppConfig;

@ContextConfiguration(classes = CoreAppConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("unittest")
public class BitcoinServiceTest {

    @Inject
    private BitcoinService bitcoinService;

    @Test
    public void bitShift() {
        // 1050 -> 0,0,4,26
        int x = (4 << 8) + (26);
        assertEquals(1050, x);

        String binary = toFullBinaryString(4) + toFullBinaryString(26);
        assertEquals(1050, Integer.parseInt(binary, 2));
    }

    public String toFullBinaryString(Integer val) {
        String padding = "00000000";
        String result = padding + Integer.toBinaryString(val);
        return result.substring(result.length() - 8, result.length());
    }

    @Test
    public void isValidAddress() {
        assertTrue(bitcoinService.isValidAddress("mjbkKLzKMdLq4FZoRCLTagtgR6sjgPs6ZM"));
        assertFalse(bitcoinService.isValidAddress("mjbkKLzKMdLq4FZoRCLTagtgR6sjgPs6Zm"));
    }

    /*
     * @Test public void createAndParseTransaction() throws
     * AddressFormatException { byte[] pubKey =
     * "mjbkKLzKMdLq4FZoRCLTagtgR6sjgPs6ZM".getBytes(); TransactionDto inputData
     * = new TransactionDto();
     * inputData.setAddressTo("mjbkKLzKMdLq4FZoRCLTagtgR6sjgPs6ZM"); String
     * amount = "0.13"; String fee = "0.0001"; inputData.setAmount(amount);
     * Currency c = new Currency(); c.setName("SJCX"); inputData.setCurrency(c);
     * inputData.setMinersFee(fee);
     * 
     * Transaction tx = bitcoinService.createTransaction(inputData, pubKey, );
     * TransactionDto txInfo = bitcoinService.parseTransaction(tx);
     * 
     * assertEquals(amount, txInfo.getAmount()); assertEquals("SJCX",
     * txInfo.getCurrency().getName()); }
     */
}