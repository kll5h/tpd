package com.tilepay.web.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ValidationServiceTest {

    private ValidationService validationService = new ValidationService();

    @Test
    public void isDouble() {
        boolean actual = validationService.isDoubleOrNumber("1,3");
        assertTrue(actual);

        actual = validationService.isDoubleOrNumber("1.3");
        assertTrue(actual);

        actual = validationService.isDoubleOrNumber("0");
        assertTrue(actual);
    }

    @Test
    public void isInteger() {
        boolean actual = validationService.isInteger("1,3");
        assertFalse(actual);

        actual = validationService.isInteger("12345");
        assertTrue(actual);
    }

    @Test
    public void validateMinimumBtcAmount() {
        boolean isValid = validationService.validateMinimumBtcAmount(0.00000547);
        assertTrue(isValid);

        isValid = validationService.validateMinimumBtcAmount(0.00000546);
        assertFalse(isValid);
    }

    @Test
    public void validateMinimumAssetAmount() {
        boolean isValid = validationService.validateMinimumAssetAmount(0.00000001);
        assertTrue(isValid);

        isValid = validationService.validateMinimumAssetAmount(0.0000000099);
        assertFalse(isValid);
    }

    @Test
    public void isIndivisibleAmountValid() {

        assertEquals(true, validationService.isIndivisibleAmountValid(true, null));
        assertEquals(true, validationService.isIndivisibleAmountValid(true, null));

        assertEquals(true, validationService.isIndivisibleAmountValid(false, "5"));
        assertEquals(false, validationService.isIndivisibleAmountValid(false, "0.1"));

    }
}
