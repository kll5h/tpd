package com.tilepay.web.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.HashMap;

import org.junit.Test;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;

public class BalanceValidatorTest {

    private BalanceValidator balanceValidator = new BalanceValidator();

    @Test
    public void validateBtcBalance() {
        boolean isValid = balanceValidator.validateBtcBalance(0, 0.00001);
        assertFalse(isValid);

        isValid = balanceValidator.validateBtcBalance(1, 0.00001);
        assertTrue(isValid);
    }

    @Test
    public void validateBtcBalance1() {
        double btcAvailableBalance = 0.00002571;
        assertFalse(balanceValidator.validateBtcBalance(btcAvailableBalance, 0.00001));

        btcAvailableBalance = 0.00002573;
        assertTrue(balanceValidator.validateBtcBalance(btcAvailableBalance, 0.00001));
    }

    @Test
    public void doesBalanceHaveEnoughFunds() {
        boolean hasEnoughFunds = balanceValidator.doesBalanceHaveEnoughFunds(BigDecimal.ZERO, BigDecimal.ZERO);
        assertTrue(hasEnoughFunds);

        hasEnoughFunds = balanceValidator.doesBalanceHaveEnoughFunds(BigDecimal.ONE, BigDecimal.valueOf(0.001d));
        assertTrue(hasEnoughFunds);

        hasEnoughFunds = balanceValidator.doesBalanceHaveEnoughFunds(BigDecimal.ZERO, BigDecimal.valueOf(0.001d));
        assertFalse(hasEnoughFunds);
    }

    @Test
    public void validateBalancesOnTransactionSend() {
        BindingResult errors = new MapBindingResult(new HashMap<>(), "");
        balanceValidator.validateBalancesOnTransactionSend(Double.valueOf(0d), Double.valueOf(0.0001d), BigDecimal.valueOf(-1d), BigDecimal.valueOf(0.015d), errors);

        assertEquals(2, errors.getErrorCount());

        TransactionValidatorTest.assertFieldError(errors, "minersFee", "transaction.BTC.amount");
        TransactionValidatorTest.assertFieldError(errors, "amount", "transaction.asset.amount.exceeds");
    }

    @Test
    public void doesBtcBalanceHaveEnoughDustAndFee() {
        boolean ok = balanceValidator.doesBtcBalanceHaveEnoughDustAndFee(new BigDecimal("0.00005143"), new BigDecimal("0.00001"), 4, 2);
        assertFalse(ok);

        ok = balanceValidator.doesBtcBalanceHaveEnoughDustAndFee(new BigDecimal("0.00005144"), new BigDecimal("0.00001"), 4, 2);
        assertTrue(ok);

        ok = balanceValidator.doesBtcBalanceHaveEnoughDustAndFee(new BigDecimal("0.00005145"), new BigDecimal("0.00001"), 4, 2);
        assertTrue(ok);

    }

}