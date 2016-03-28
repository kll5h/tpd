package com.tilepay.core.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import org.bitcoinj.core.Coin;
import org.bitcoinj.core.TransactionOutput;
import org.bitcoinj.core.Wallet;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.tilepay.core.dto.AssetIssuanceDto;
import com.tilepay.core.dto.TransactionDto;
import com.tilepay.core.service.balance.TilecoinRestClient;
import com.tilepay.domain.entity.Asset;
import com.tilepay.domain.entity.Balance;
import com.tilepay.domain.entity.Send;
import com.tilepay.domain.entity.Transaction;
import com.tilepay.protocol.CoinConstants;

@Service
public class TransactionService {

    @Inject
    @Qualifier("protocolBitcoinService")
    private com.tilepay.protocol.service.BitcoinService protocolBitcoinService;

    @Inject
    private BitcoinService bitcoinService;

    @Inject
    private TilecoinRestClient tilecoinRestClient;

    @Inject
    private TransactionConverter transactionConverter;

    public String findInputHash(Wallet wallet, BigDecimal btcMinersFee) {
        List<TransactionOutput> outputList = wallet.calculateAllSpendCandidates(true, true);
        OrderedCoinSelector coinSelector = new OrderedCoinSelector();
        Collection<TransactionOutput> outputs = coinSelector.select(getBiTarget(btcMinersFee), outputList).gathered;
        return outputs.isEmpty() ? null : outputs.iterator().next().getParentTransaction().getHash().toString();
    }
    
    public AssetCreationResult createAssetWithBalance(AssetIssuanceDto form, Wallet wallet) {

        AssetCreationResult assetCreationResult = new AssetCreationResult();

        Transaction transaction = transactionConverter.getIssuanceTransaction(form);
        Wallet.SendResult sendResult = protocolBitcoinService.send(wallet, transaction, form.getPassword(), transactionHash -> {
        });

        assetCreationResult.assetCreationTxSendResult = sendResult;

        if (sendResult != null) {
        	Asset asset = transaction.getMessage().getAsset();
        	Balance balance = new Balance();
        	balance.setAsset(asset);
        	balance.setAddress(form.getSource());
        	balance.setQuantity(new BigInteger(form.getQuantity()));
        	tilecoinRestClient.saveAssetWithBalance(balance);
            TransactionDto feeTransactionDto = transactionConverter.getFeeTransactionDto(form);
            Transaction feeTransaction = transactionConverter.getCounterpartySendTransaction(feeTransactionDto);
            String inputHash = findInputHash(wallet, new BigDecimal(CoinConstants.ASSET_CREATION_FEE_IN_BTC));
            if (inputHash != null) {
                assetCreationResult.feeTxSendResult = bitcoinService.sendTransaction(feeTransactionDto, wallet, transactionHash -> {
                }, (Send) feeTransaction.getMessage(), inputHash);
            }

        }
        return assetCreationResult;
    }

    public Coin getBiTarget(BigDecimal btcMinersFee) {
        BigDecimal requiredBtc = btcMinersFee.add(new BigDecimal("2").multiply(CoinConstants.TX_DUST_AS_BIG_DECIMAL));
        return Coin.parseCoin(requiredBtc.toString());

    }
}
