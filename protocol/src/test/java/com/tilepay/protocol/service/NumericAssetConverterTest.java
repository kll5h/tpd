package com.tilepay.protocol.service;

import static org.junit.Assert.assertEquals;

import java.math.BigInteger;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class NumericAssetConverterTest {

    private NumericAssetConverter converter = new NumericAssetConverter();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void getAssetId() {
        BigInteger assetId = converter.getAssetId("A16876119118765670000");
        assertEquals(new BigInteger("16876119118765670000"), assetId);
    }

    @Test
    public void assetNameShouldStartWithA() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Numeric asset name: B16876119118765670000 should start with 'A'");

        converter.getAssetId("B16876119118765670000");
    }

    @Test
    public void assetNameShouldStartWithAAndFollowByNumbers() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Numeric asset name: AA16876119118765670000 should start with 'A' followed by numbers");

        converter.getAssetId("AA16876119118765670000");
    }

    @Test
    public void minRange() {
        BigInteger assetId = converter.getAssetId("A95428956661682178");
        assertEquals(new BigInteger("95428956661682178"), assetId);

        assetId = converter.getAssetId("A95428956661682177");
        assertEquals(new BigInteger("95428956661682177"), assetId);

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Numeric asset id: 95428956661682176 should be in range: [95428956661682177, 18446744073709551615]");
        converter.getAssetId("A95428956661682176");
    }

    @Test
    public void maxRange() {
        BigInteger assetId = converter.getAssetId("A18446744073709551614");
        assertEquals(new BigInteger("18446744073709551614"), assetId);

        assetId = converter.getAssetId("A18446744073709551615");
        assertEquals(new BigInteger("18446744073709551615"), assetId);

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Numeric asset id: 18446744073709551616 should be in range: [95428956661682177, 18446744073709551615]");
        converter.getAssetId("A18446744073709551616");
    }

    @Test
    public void getAssetName() {
        String assetName = converter.getAssetName(new BigInteger("95428956661682177"));
        assertEquals("A95428956661682177", assetName);
    }

}