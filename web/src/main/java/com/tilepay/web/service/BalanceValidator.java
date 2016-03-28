package com.tilepay.web.service;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.tilepay.protocol.CoinConstants;

@Component
public class BalanceValidator {

    public Boolean validateBtcBalance(double btcBalance, double btcMinersFee) {
        return doesBtcBalanceHaveEnoughDustAndFee(new BigDecimal(btcBalance), new BigDecimal(btcMinersFee), 2, 1);
    }

    public boolean doesBtcBalanceHaveEnoughDustAndFee(BigDecimal btcBalance, BigDecimal btcMinersFee, int numberOfDusts, int numberOfFees) {
        BigDecimal requiredDust = new BigDecimal(numberOfDusts).multiply(CoinConstants.TX_DUST_AS_BIG_DECIMAL);
        BigDecimal requiredBtcMinersFee = new BigDecimal(numberOfFees).multiply(btcMinersFee);
        BigDecimal requiredBtc = requiredDust.add(requiredBtcMinersFee);
        return btcBalance.compareTo(requiredBtc) >= 0;
    }

    public void validateBalancesOnTransactionSend(Double btcBalance, Double btcMinersFee, BigDecimal assetBalance, BigDecimal assetQuantity, Errors errors) {

        if (btcMinersFee != null && !validateBtcBalance(btcBalance, btcMinersFee)) {
            errors.rejectValue("minersFee", "transaction.BTC.amount");
        }

        if (!doesBalanceHaveEnoughFunds(assetBalance, assetQuantity)) {
            errors.rejectValue("amount", "transaction.asset.amount.exceeds");
        }
    }

    public boolean doesBalanceHaveEnoughFunds(BigInteger balance, BigDecimal funds) {
        return doesBalanceHaveEnoughFunds(new BigDecimal(balance), funds);
    }

    // TODO: 19.11.2014 Andrei Sljusar: check if negative
    public boolean doesBalanceHaveEnoughFunds(BigDecimal balance, BigDecimal funds) {
        return funds.compareTo(balance) <= 0;
    }

    public boolean validateAmount(BigDecimal balance, BigDecimal amount, BigDecimal minersFee) {
        if (minersFee != null) {
            BigDecimal finalAmount = amount.add(minersFee);
            return doesBalanceHaveEnoughFunds(balance, finalAmount);
        }
        return false;
    }

}
