package com.tilepay.core.service;

import static org.bitcoinj.script.ScriptOpCodes.OP_1;
import static org.bitcoinj.script.ScriptOpCodes.OP_2;
import static org.bitcoinj.script.ScriptOpCodes.OP_3;
import static org.bitcoinj.script.ScriptOpCodes.OP_CHECKMULTISIG;
import static org.bitcoinj.script.ScriptOpCodes.OP_RETURN;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.bitcoinj.core.Address;
import org.bitcoinj.core.AddressFormatException;
import org.bitcoinj.core.Transaction;
import org.bitcoinj.core.TransactionConfidence.ConfidenceType;
import org.bitcoinj.core.TransactionInput;
import org.bitcoinj.core.TransactionOutput;
import org.bitcoinj.core.Wallet;
import org.bitcoinj.kits.WalletAppKit;
import org.bitcoinj.script.Script;
import org.bitcoinj.script.ScriptChunk;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.tilepay.core.config.NetworkParams;
import com.tilepay.core.dto.BTCTransactionDto;
import com.tilepay.core.dto.DeviceRegistrationDto;
import com.tilepay.core.dto.InputDto;
import com.tilepay.core.dto.OutputDto;
import com.tilepay.core.dto.TransactionDto;
import com.tilepay.domain.entity.Send;
import com.tilepay.protocol.CoinConstants;
import com.tilepay.protocol.service.ProtocolService;
import com.tilepay.protocol.service.TransactionSentCallback;

@Service
public class BitcoinService {

    @Inject
    private NetworkParams networkParams;

    @Inject
    private WalletAppKitHolder walletAppKitHolder;

    @Inject
    private AssetTransactionService assetTransactionService;

    @Inject
    private AddressService addressService;

    @Inject
    @Qualifier("protocolBitcoinService")
    private com.tilepay.protocol.service.BitcoinService protocolBitcoinService;

    @Inject
    private ProtocolService protocolService;

    @Inject
    private OutputParsingService outputParsingService;

    public Wallet.SendResult sendTransaction(TransactionDto inputData, Wallet wallet, TransactionSentCallback transactionSentCallback, Send send, String inputHash) {
        String source = protocolBitcoinService.getAddress(inputData.getAddressFrom()).toString();

        Wallet.SendResult sendResult = protocolBitcoinService.sendTransaction(wallet, source, inputData.getAddressTo(), inputData.getAmount(), inputHash, send,
                inputData.getPassword(), inputData.getMinersFee(), transactionSentCallback);

        if (sendResult != null) {
            assetTransactionService.storeAssetTx(addressService.findOneByAddress(source), sendResult.tx.getHashAsString(), inputData);
        }

        return sendResult;
    }

    public Wallet.SendResult sendOPTransaction(DeviceRegistrationDto inputData, Wallet wallet, String source, String token, TransactionSentCallback transactionSentCallback) {
        return protocolBitcoinService.sendOPReturnTransaction(wallet, token, source, inputData.getPassword(), CoinConstants.DEVICE_REGISTRATION_FEE_IN_BTC, transactionSentCallback);
    }

    public boolean isValidAddress(String address) {
        try {
            new Address(networkParams.get(), address);
            return true;
        } catch (AddressFormatException e) {
            return false;
        }
    }

    public List<BTCTransactionDto> convertTransactionsToBTCTransactionsDTO(Long walletId) {
        WalletAppKit kit = walletAppKitHolder.getWalletAppKit(walletId);
        Set<Transaction> txs = kit.wallet().getTransactions(false);
        List<BTCTransactionDto> transactions = new ArrayList<>();

        for (Transaction tx : txs) {
            BTCTransactionDto transaction = new BTCTransactionDto();

            transaction.setHash(tx.getHashAsString());
            transaction.setUpdateTime(tx.getUpdateTime());

            if (tx.getConfidence().getConfidenceType() == ConfidenceType.PENDING) {
                transaction.setBlock(null);
            } else {
                transaction.setBlock(tx.getConfidence().getAppearedAtChainHeight());
            }
            transaction.setFee(tx.getFee() == null ? null : tx.getFee().toFriendlyString());

            // TODO: how to properly distinguish incoming/outgoing txs
            for (TransactionInput txInput : tx.getInputs()) {
                InputDto input = new InputDto();
                input.setAddress(txInput.getFromAddress().toString());
                input.setAmount(txInput.getValue() == null ? "0" : txInput.getValue().toFriendlyString());
                input.setScript("");
                transaction.getInputs().add(input);
            }

            for (TransactionOutput txOutput : tx.getOutputs()) {
                Script sc = txOutput.getScriptPubKey();
                OutputDto output = outputParsingService.parseOutput(txOutput);
                List<ScriptChunk> chunks = sc.getChunks();
                byte[] data = null;

                if (sc.isOpReturn()) {
                    if (chunks.size() == 2 && chunks.get(0).equalsOpCode(OP_RETURN) && protocolService.isIoTProtocol(chunks.get(1).data)) {
                        outputParsingService.parseIoTOutput(chunks.get(1).data, output);
                    }
                }

                if (sc.isSentToMultiSig()) {
                    // TODO: 29.12.2014 Andrei Sljusar: validate
                    // data(pubKey).data(data)
                    // return new
                    // ScriptBuilder().op(ScriptOpCodes.OP_1).data(pubKey).data(data).op(ScriptOpCodes.OP_2).op(ScriptOpCodes.OP_CHECKMULTISIG).build();
                    if (chunks.size() == 5 && chunks.get(0).equalsOpCode(OP_1) && chunks.get(3).equalsOpCode(OP_2)
                            && chunks.get(4).equalsOpCode(OP_CHECKMULTISIG)) {
                        data = chunks.get(2).data;
                    }

                    // TODO: 29.12.2014 Andrei Sljusar: validate
                    // data(chunk1).data(chunk2).data(pubKey)
                    // return new
                    // ScriptBuilder().op(ScriptOpCodes.OP_1).data(chunk1).data(chunk2).data(pubKey).op(ScriptOpCodes.OP_3).op(ScriptOpCodes.OP_CHECKMULTISIG).build();
                    if (chunks.size() == 6 && chunks.get(0).equalsOpCode(OP_1) && chunks.get(4).equalsOpCode(OP_3)
                            && chunks.get(5).equalsOpCode(OP_CHECKMULTISIG)) {
                        data = protocolService.extract(tx, chunks);
                    }

                    outputParsingService.parseAssetOutput(data, output);
                }
                transaction.addOutput(output);
            }

            transaction.sortOutputs();
            outputParsingService.setTxType(tx, transaction);
            
            if (transaction.getType() != BTCTransactionDto.transactionType.RECEIVING) {
                List<OutputDto> outputs = transaction.getOutputs();
                OutputDto outputDto = outputs.get(0);
                if (outputDto.isDeviceRegistration()) {
                    if (outputs.size() > 1) {
                        outputDto = outputs.get(1);
                        outputDto.setChange(true);
                    }
                } else if (outputDto.isAssetExists()) {
                    if (outputs.size() > 2) {
                        outputDto = outputs.get(2);
                        outputDto.setChange(true);
                    }
                } else {
                    if (outputs.size() >= 2) {
                        outputDto = outputs.get(outputs.size() - 1);
                        outputDto.setChange(true);
                    }
                }
            }

            transactions.add(transaction);
        }

        return transactions;
    }

}
