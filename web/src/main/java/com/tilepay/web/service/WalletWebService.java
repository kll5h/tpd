package com.tilepay.web.service;

import static com.tilepay.domain.entity.SendBuilder.aSend;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.bitcoinj.core.Wallet;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.tilepay.core.dto.AssetIssuanceDto;
import com.tilepay.core.dto.DeviceRegistrationDto;
import com.tilepay.core.dto.ResponseDTO;
import com.tilepay.core.dto.TransactionDto;
import com.tilepay.core.dto.WalletDTO;
import com.tilepay.core.model.Account;
import com.tilepay.core.service.AssetCreationResult;
import com.tilepay.core.service.BitcoinService;
import com.tilepay.core.service.TransactionConverter;
import com.tilepay.core.service.TransactionService;
import com.tilepay.core.service.WalletAppKitHolder;
import com.tilepay.core.service.balance.BalanceServiceFactory;
import com.tilepay.domain.entity.Asset;
import com.tilepay.domain.entity.Send;
import com.tilepay.domain.entity.Transaction;

@Service
public class WalletWebService {

    @Inject
    private SessionService sessionService;

    @Inject
    private WalletAppKitHolder walletAppKitHolder;

    @Inject
    private BitcoinService bitcoinService;

    @Inject
    @Qualifier("protocolBitcoinService")
    private com.tilepay.protocol.service.BitcoinService protocolBitcoinService;

    @Inject
    private TransactionService transactionService;

    @Inject
    private BalanceServiceFactory balanceServiceFactory;

    @Inject
    private TransactionValidator transactionValidator;

    @Inject
    private TransactionConverter transactionConverter;

    // TODO: 02.01.2015 Andrei Sljusar: test
    public void send(TransactionDto form, ResponseDTO response) {
        Account account = sessionService.getAccount();

        Wallet.SendResult sendResult;
        Wallet wallet = walletAppKitHolder.getWalletAppKit(account.getId()).wallet();

        if (form.getAsset().isCntrprty()) {
            Transaction transaction = transactionConverter.getCounterpartySendTransaction(form);
            String inputHash = transactionService.findInputHash(wallet, form.getMinersFeeAsBigDecimal());
            sendResult = bitcoinService.sendTransaction(form, wallet, response::setMessage, (Send) transaction.getMessage(), inputHash);
        } else if (form.getAsset().isTilecoin()) {
            Transaction transaction = transactionConverter.getSendTransaction(form);
            //TODO: 22.01.2015 Andrei Sljusar: miners fee
            sendResult = protocolBitcoinService.send(wallet, transaction, form.getPassword(), response::setMessage);
        } else if (form.getAsset().isBitcoin()) {
            Send send = aSend().setAsset(form.getAsset()).build();
            sendResult = bitcoinService.sendTransaction(form, wallet, response::setMessage, send, null);
        } else {
            throw new IllegalArgumentException("Unknown protocol: " + form.getAsset().getProtocol());
        }

        response.setSuccessful(sendResult != null);
    }
    
    public AssetCreationResult createAssetWithBalance(AssetIssuanceDto form) {
        Account account = sessionService.getAccount();
        Wallet wallet = walletAppKitHolder.getWalletAppKit(account.getId()).wallet();
        String source = account.getWallet().getAddress().getAddress();
        form.setSource(source);
        return transactionService.createAssetWithBalance(form, wallet);
    }

    public void confirm(WalletDTO wallet, TransactionDto transaction, BindingResult result) {
        Asset asset = transaction.getAsset();

        if (StringUtils.isNotBlank(asset.getProtocol())) {
            balanceServiceFactory.getBalanceService(asset).setAssetDivisibility(asset);
        }
        transactionValidator.validate(transaction, wallet, result);
    }
    
    public Wallet.SendResult sendOPReturnTx(DeviceRegistrationDto form, String token, ResponseDTO response){
    	Account account = sessionService.getAccount();
    	Wallet wallet = walletAppKitHolder.getWalletAppKit(account.getId()).wallet();
    	
    	return bitcoinService.sendOPTransaction(form, wallet,account.getWallet().getAddress().getAddress(), token, response::setMessage);
    }
}
