package com.tilepay.core.service;

import static org.junit.Assert.assertEquals;

import java.math.BigInteger;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.tilepay.domain.entity.Asset;
import com.tilepay.domain.entity.AssetBuilder;

public class AssetQuantityConversionServiceTest {

    private AssetQuantityConversionService assetQuantityConversionService = new AssetQuantityConversionService();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void format() {
        Asset asset = AssetBuilder.anAsset().setDivisible(true).build();
        String actual = assetQuantityConversionService.formatAsString(asset, new BigInteger("100"));
        assertEquals("0.000001", actual);

        asset = AssetBuilder.anAsset().setDivisible(false).build();
        actual = assetQuantityConversionService.formatAsString(asset, new BigInteger("100"));
        assertEquals("100", actual);
    }

    @Test
    public void formatDivisibleMustBeDefinedException() {
        Asset asset = AssetBuilder.anAsset().setName("AAA").build();

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Divisible must be defined for: AAA");

        assetQuantityConversionService.format(asset, new BigInteger("100"));
    }

    @Test
    public void formatAsBigInteger() {
        Asset asset = AssetBuilder.anAsset().setDivisible(true).build();
        BigInteger actual = assetQuantityConversionService.formatAsBigInteger(asset, "0.000001");
        assertEquals(new BigInteger("100"), actual);

        asset = AssetBuilder.anAsset().setDivisible(false).build();
        actual = assetQuantityConversionService.formatAsBigInteger(asset, "100");
        assertEquals(new BigInteger("100"), actual);
    }

    @Test
    public void formatAsBigIntegerDivisibleMustBeDefinedException() {
        Asset asset = AssetBuilder.anAsset().setName("AAA").build();

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Divisible must be defined for: AAA");

        assetQuantityConversionService.formatAsBigInteger(asset, "0.000001");
    }

    @Test
    public void getMultipliedBigInteger() {
        BigInteger actual = assetQuantityConversionService.getMultipliedBigInteger("0.002");
        assertEquals(new BigInteger("200000"), actual);
    }
}