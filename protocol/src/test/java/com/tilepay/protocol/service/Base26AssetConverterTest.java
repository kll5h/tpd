package com.tilepay.protocol.service;

import static org.junit.Assert.assertEquals;

import java.math.BigInteger;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class Base26AssetConverterTest {

    private Base26AssetConverter converter = new Base26AssetConverter();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void getAssetId() throws Exception {
        BigInteger assetId = converter.getAssetId("INTEST");
        assertEquals(101328831, assetId.intValue());

        assetId = converter.getAssetId("TILECOINXTC");
        assertEquals(4035415473177L, assetId.longValue());
    }

    @Test
    public void getAssetName() {
        String assetName = converter.getAssetName(new BigInteger("101328831"));
        assertEquals("INTEST", assetName);

        assetName = converter.getAssetName(new BigInteger("4035415473177"));
        assertEquals("TILECOINXTC", assetName);

        assetName = converter.getAssetName(BigInteger.ZERO);
        assertEquals("BTC", assetName);

        assetName = converter.getAssetName(BigInteger.ONE);
        assertEquals("XCP", assetName);
    }

    @Test
    public void getMinAssetId() {
        BigInteger assetId = converter.getAssetId("BAAA");
        assertEquals(17576L, assetId.longValue());
    }

    @Test
    public void getMaxAssetName() {
        String assetName = converter.getAssetName(new BigInteger("95428956661682175"));
        assertEquals("ZZZZZZZZZZZZ", assetName);

        BigInteger assetId = converter.getAssetId("ZZZZZZZZZZZZ");
        assertEquals(new BigInteger("95428956661682175"), assetId);
    }

    @Test
    public void cantGenerateAssetName() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Can't generate asset name from asset id: 18446744073709551615");
        converter.getAssetName(new BigInteger("18446744073709551615"));
    }

    @Test
    public void assetNameShouldBeAtLeast4Characters() {
        BigInteger assetId = converter.getAssetId("BBCD");
        assertEquals(new BigInteger("18307"), assetId);

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("ABC should be at least 4 characters");
        converter.getAssetId("ABC");
    }

    @Test
    public void min() {
        String assetName = converter.getAssetName(new BigInteger("17576"));
        assertEquals("BAAA", assetName);

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("17575 is too short. Should be < 17576");
        converter.getAssetName(new BigInteger("17575"));
    }

    @Test
    public void shouldThrowInvalidCharacterException() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Invalid character: 1");
        converter.getAssetId("BBCD1");
    }

    @Test
    public void maxLengthException() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Base 26 asset name max length: 12. XXXXXXXXXXXXX is 13.");
        converter.getAssetId("XXXXXXXXXXXXX");
    }

}