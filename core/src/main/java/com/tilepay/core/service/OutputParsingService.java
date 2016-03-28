package com.tilepay.core.service;

import javax.inject.Inject;

import org.bitcoinj.core.Transaction;
import org.bitcoinj.core.TransactionInput;
import org.bitcoinj.core.TransactionOutput;
import org.springframework.stereotype.Service;

import com.tilepay.core.config.NetworkParams;
import com.tilepay.core.dto.BTCTransactionDto;
import com.tilepay.core.dto.BTCTransactionDto.transactionType;
import com.tilepay.core.dto.OutputDto;
import com.tilepay.core.service.balance.TilecoinRestClient;
import com.tilepay.domain.entity.Asset;
import com.tilepay.domain.entity.AssetBuilder;
import com.tilepay.domain.entity.Issuance;
import com.tilepay.domain.entity.Message;
import com.tilepay.domain.entity.Send;
import com.tilepay.protocol.config.NetworkParametersConfig;
import com.tilepay.protocol.service.CntrprtySendService;
import com.tilepay.protocol.service.MessageFactory;
import com.tilepay.protocol.service.ProtocolService;

@Service
public class OutputParsingService {

    @Inject
    private CntrprtySendService cntrprtySendService;

    @Inject
    private MessageFactory messageFactory;

    @Inject
    private AssetQuantityConversionService assetQuantityConversionService;

    @Inject
    private NetworkParams networkParams;

    @Inject
    private AssetConverter assetConverter;

    @Inject
    private ProtocolService protocolService;

    @Inject
    private TilecoinRestClient tilecoinRestClient;

    @Inject
    private NetworkParametersConfig networkParametersConfig;

    protected OutputDto parseOutput(TransactionOutput txOutput) {
        OutputDto output = new OutputDto();
        if (txOutput.getScriptPubKey().isSentToAddress()) {
            output.setAddress(txOutput.getScriptPubKey().getToAddress(networkParams.get()).toString());
        }
        output.setAmount(txOutput.getValue().toFriendlyString());
        return output;
    }

    public void parseCounterPartyOutput(byte[] data, OutputDto output) {
        Send send = cntrprtySendService.parse(data);
        Asset asset = assetConverter.getAsset(send.getAssetName());
        setOutput(send, output, asset);
    }

    public void parseTilecoinOutput(byte[] data, OutputDto output) {
        Message message = messageFactory.get(data);
        Asset asset = tilecoinRestClient.getAssetByName(message.getAssetName());
        if (asset == null) {
            asset = AssetBuilder.anAsset().setName(message.getAssetName()).setTilecoinProtocol().setDivisible(true).build();
        }
        setOutput(message, output, asset);
    }
    
    public void parseIoTOutput(byte[] data, OutputDto output){
        output.setDeviceRegistration(true);
    }

    public void setOutput(Message message, OutputDto output, Asset asset) {
        if (asset != null) {
            output.setAsset(asset);
            output.setIssuance(message instanceof Issuance);
            output.setAssetAmount(assetQuantityConversionService.formatAsString(asset, message.getQuantity()));
        }
    }

    public void parseAssetOutput(byte[] data, OutputDto output) {
        if (protocolService.isCntrprtyProtocol(data)) {
            parseCounterPartyOutput(data, output);
        }
        if (protocolService.isTilecoinProtocol(data)) {
            parseTilecoinOutput(data, output);
        }
    }

    public void setTxType(Transaction tx, BTCTransactionDto transaction) {
        // TODO: Jan 23, 2015 Mait Mikkelsaar: different service as it includes
        // not only output
        if (txIsReceiving(tx)) {
            transaction.setType(transactionType.RECEIVING);
        } else {
            setOutgoingTxMessage(transaction);
        }
    }

    private void setOutgoingTxMessage(BTCTransactionDto transaction) {
        if (transaction.getType() == null) {
            if (txIsIssuence(transaction)) {
                transaction.setType(transactionType.ISSUING);
            } else if (txIsFee(transaction)) {
                transaction.setType(transactionType.FEE);
            } else {
                transaction.setType(transactionType.SENDING);
            }
        }
    }

    private boolean txIsReceiving(Transaction tx) {
        for (TransactionInput input : tx.getInputs()) {
            if (input.getValue() == null) {
                return true;
            }
        }
        return false;
    }

    private boolean txIsFee(BTCTransactionDto transaction) {
        boolean hasFeeAddress = false;
        for (OutputDto output : transaction.getOutputs()) {
            if (output.getAddress() != null && output.getAddress().equals(networkParametersConfig.getFeeAddress())) {
                hasFeeAddress = true;
            }
        }
        return hasFeeAddress;
    }

    private boolean txIsIssuence(BTCTransactionDto transaction) {
        for (OutputDto output : transaction.getOutputs()) {
            if (output.isIssuance()) {
                return true;
            }
        }
        return false;
    }

}
