package com.tilepay.core.service;

import static com.tilepay.domain.entity.AssetBuilder.anAsset;
import static com.tilepay.domain.entity.IssuanceBuilder.anIssuance;
import static com.tilepay.domain.entity.SendBuilder.aSend;
import static com.tilepay.domain.entity.TransactionBuilder.aTransaction;

import com.tilepay.core.dto.AssetIssuanceDto;
import com.tilepay.core.dto.TransactionDTOBuilder;
import com.tilepay.core.dto.TransactionDto;
import com.tilepay.domain.entity.Asset;
import com.tilepay.domain.entity.CurrencyEnum;
import com.tilepay.domain.entity.Issuance;
import com.tilepay.domain.entity.Message;
import com.tilepay.domain.entity.Transaction;
import com.tilepay.domain.entity.TransactionBuilder;
import com.tilepay.protocol.CoinConstants;
import com.tilepay.protocol.config.NetworkParametersConfig;
import com.tilepay.protocol.service.CntrprtySendService;
import com.tilepay.protocol.service.IssuanceService;
import com.tilepay.protocol.service.MessageService;
import com.tilepay.protocol.service.SendService;
import java.math.BigInteger;
import javax.inject.Inject;
import org.springframework.stereotype.Component;

@Component
public class TransactionConverter {

    @Inject
    private NetworkParametersConfig networkParametersConfig;

    @Inject
    private SendService sendService;

    @Inject
    private CntrprtySendService cntrprtySendService;

    @Inject
    private AssetConverter assetConverter;

    @Inject
    private IssuanceService issuanceService;

    @Inject
    private AssetQuantityConversionService assetQuantityConversionService;

    public TransactionDto getFeeTransactionDto(AssetIssuanceDto assetIssuance) {
        Asset asset = anAsset().setName(CurrencyEnum.TILECOINXTC.name()).setCntrprtyProtocol().build();
        return TransactionDTOBuilder.aTransaction().
                setSource(assetIssuance.getSource()).
                setDestination(networkParametersConfig.getFeeAddress()).
                setAsset(asset).
                withAmount("50").
                setMinersFee(CoinConstants.ASSET_CREATION_FEE_IN_BTC).
                setPassword(assetIssuance.getPassword()).
                build();

    }

    public Transaction getCounterpartySendTransaction(TransactionDto form) {
        return getSendTransaction(form, cntrprtySendService);
    }

    public Transaction getSendTransaction(TransactionDto form) {
        return getSendTransaction(form, sendService);
    }

    public Transaction getSendTransaction(TransactionDto form, MessageService messageService) {
        BigInteger quantity = assetQuantityConversionService.getQuantity(form.getAsset(), form.getAmount());
        Message send = aSend().setAsset(form.getAsset()).setQuantity(quantity).build();
        messageService.compose(send);
        return aTransaction().setSource(form.getAddressFrom()).setDestination(form.getAddressTo()).setMessage(send).build();
    }

    public Transaction getIssuanceTransaction(AssetIssuanceDto form) {
        Asset asset = assetConverter.getAsset(form);
        BigInteger quantity = assetQuantityConversionService.formatAsBigInteger(asset, form.getQuantity());
        Issuance issuance = anIssuance().setAsset(asset).setQuantity(quantity).build();
        issuanceService.compose(issuance);
        return TransactionBuilder.aTransaction().setSource(form.getSource()).setDestination(form.getSource()).setMessage(issuance).build();
    }
}
