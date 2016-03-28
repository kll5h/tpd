package com.tilepay.web.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.tilepay.core.constant.RegExpPattern;

@Service
public class ValidationService {

    public static final Double MINIMUM_BTC_AMOUNT = 0.00000547;
    public static final Double MINIMUM_ASSET_AMOUNT = 0.00000001;

    public boolean isDoubleOrNumber(String number) {
        return number.matches(RegExpPattern.DOUBLE_NUMBER);
    }

    public boolean isInteger(String number) {
        return number.matches(RegExpPattern.INTEGER);
    }

    public boolean validateMinimumAssetAmount(Double amount) {
        Assert.notNull(amount);
        return amount >= MINIMUM_ASSET_AMOUNT;
    }

    public boolean validateMinimumBtcAmount(BigDecimal amount) {
        return validateMinimumBtcAmount(amount.doubleValue());
    }

    public boolean validateMinimumBtcAmount(Double amount) {
        return amount >= MINIMUM_BTC_AMOUNT;
    }

    public boolean isIndivisibleAmountValid(Boolean divisible, String amount) {
        return divisible || isInteger(amount);
    }

}
