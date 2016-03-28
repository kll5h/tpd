package com.tilepay.core.service;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.tilepay.counterpartyclient.model.AssetInfo;
import com.tilepay.counterpartyclient.model.AssetInfoBuilder;
import com.tilepay.counterpartyclient.service.CounterpartyService;
import com.tilepay.domain.entity.Asset;

@RunWith(MockitoJUnitRunner.class)
public class AssetConverterTest {

    @Mock
    private CounterpartyService counterpartyService;

    @InjectMocks
    private AssetConverter assetConverter;

    @Test
    public void getAssetFromCounterparty() throws Exception {

        AssetInfo assetInfo = AssetInfoBuilder.anAssetInfo().setAsset("AAA").setDivisible(true).build();

        Mockito.when(counterpartyService.getOneAssetInfo("AAA")).thenReturn(assetInfo);

        Asset asset = assetConverter.getAsset("AAA");

        assertEquals("AAA", asset.getName());
        assertEquals(true, asset.getDivisible());
    }

    @Test
    public void getAsset() throws Exception {

        AssetInfo assetInfo = AssetInfoBuilder.anAssetInfo().setAsset("AAA").setDivisible(true).build();
        Asset asset = assetConverter.getAsset(assetInfo);

        assertEquals("AAA", asset.getName());
        assertEquals(true, asset.getDivisible());
    }
}