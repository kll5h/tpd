package com.tilepay.core.service;

import java.math.BigDecimal;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tilepay.core.dto.TransactionDto;
import com.tilepay.core.model.Address;
import com.tilepay.core.model.AssetTransaction;
import com.tilepay.core.repository.AssetTransactionRepository;
import com.tilepay.domain.entity.CurrencyEnum;

@Service
public class AssetTransactionService {

    @Inject
    private AssetTransactionRepository transactionRepository;

    @Transactional
    public void deleteOneByHash(String hash) {
        transactionRepository.deleteOneByHash(hash);
    }

    public List<AssetTransaction> findAllWhereAddressAndCurrency(Address address, String currency) {
        return transactionRepository.findAllWhereAddressAndCurrency(address, currency);
    }

    public void storeAssetTx(Address address, String hashAsString, TransactionDto inputData) {
        if (!inputData.getAsset().getName().equals(CurrencyEnum.BTC.toString())) {
            AssetTransaction transaction = new AssetTransaction();
            transaction.setAddress(address);
            transaction.setHash(hashAsString);
            transaction.setAmount(new BigDecimal(inputData.getAmount()));
            transaction.setCurrency(inputData.getAsset().getName());
            transactionRepository.save(transaction);
        }
    }
}
