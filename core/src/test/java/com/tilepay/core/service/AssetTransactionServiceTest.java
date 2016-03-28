package com.tilepay.core.service;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.tilepay.core.dto.TransactionDto;
import com.tilepay.core.model.Address;
import com.tilepay.core.model.AssetTransaction;
import com.tilepay.core.repository.AssetTransactionRepository;
import com.tilepay.domain.entity.Asset;
import com.tilepay.domain.entity.AssetBuilder;

@RunWith(MockitoJUnitRunner.class)
public class AssetTransactionServiceTest {

    @Mock
    private AssetTransactionRepository transactionRepository;

    @InjectMocks
    private AssetTransactionService assetTransactionService;

    @Test
    public void storeAssetTx() {
        Address address = new Address();
        address.setAddress("address123");
        String hashAsString = "hash123";

        Asset currency = AssetBuilder.anAsset().setName("XTC").build();

        TransactionDto inputData = new TransactionDto();
        inputData.setAmount("1.0");
        inputData.setAsset(currency);

        assetTransactionService.storeAssetTx(address, hashAsString, inputData);

        verify(transactionRepository).save(any(AssetTransaction.class));
    }
}