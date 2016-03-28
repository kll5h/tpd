package com.tilepay.protocol.service;

import static com.tilepay.domain.entity.TransactionBuilder.aTransaction;
import static org.bitcoinj.script.ScriptOpCodes.OP_1;
import static org.bitcoinj.script.ScriptOpCodes.OP_2;
import static org.bitcoinj.script.ScriptOpCodes.OP_CHECKMULTISIG;
import static org.bitcoinj.script.ScriptOpCodes.OP_RETURN;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.concurrent.ThreadSafe;
import javax.inject.Inject;

import org.bitcoinj.core.Address;
import org.bitcoinj.core.ScriptException;
import org.bitcoinj.core.Transaction;
import org.bitcoinj.core.TransactionInput;
import org.bitcoinj.core.TransactionOutput;
import org.bitcoinj.script.Script;
import org.bitcoinj.script.ScriptChunk;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.tilepay.domain.entity.Message;
import com.tilepay.protocol.config.NetworkParametersConfig;

@ThreadSafe
@Service
public class ProtocolTransactionService {

    private static final Logger logger = LoggerFactory.getLogger(ProtocolTransactionService.class);
    
    private static final Integer IOTBlockHeightTestnet = 321518;

    @Inject
    private ProtocolService protocolService;

    @Inject
    private MessageFactory messageFactory;

    @Inject
    private NetworkParametersConfig networkParametersConfig;

    //TODO: 25.11.2014 Andrei Sljusar: Destination is the first output before the data. output[0]
    public com.tilepay.domain.entity.Transaction parse(Integer blockIndex, Transaction btcTransaction) {

        //TODO: 27.12.2014 Andrei Sljusar:
        /*# Ignore coinbase transactions.
    if ctx.is_coinbase(): raise DecodeError('coinbase transaction')*/
        //btcTransaction.isCoinBase();

        List<TransactionOutput> outputs = btcTransaction.getOutputs();
        
        //outputs:

        //destination: 0.0000078
        //TODO: 20.12.2014 Andrei Sljusar: many multisig outputs
        //TODO: 28.12.2014 Andrei Sljusar: multisig 1-of-3. can be 2 data
        //multisig 1-of-2: 1, source, data, 2, CHECKMULTISIG: 0.0000078
        //source (change): 0.00466333

        //TODO: 02.12.2014 Andrei Sljusar: fee -= vout['value'] * config.UNIT
        //TODO: 08.12.2014 Andrei Sljusar: //check to see if this is a burn

        //TODO: 08.12.2014 Andrei Sljusar: test size 3
        //TODO: 08.12.2014 Andrei Sljusar: can be size 2 if no change?
        if (outputs.size() == 2 || outputs.size() == 3) {

            TransactionOutput output = outputs.get(1);

            boolean sentToMultiSig = false;
            boolean sentToOpReturn = false;
            try {
                sentToMultiSig = output.getScriptPubKey().isSentToMultiSig();
                sentToOpReturn = outputs.get(0).getScriptPubKey().isOpReturn();
            } catch (ScriptException e) {
                logger.error("TODO: ", e);
            }
            
            if(sentToOpReturn){
                output = outputs.get(0);
                List<ScriptChunk> chunks = output.getScriptPubKey().getChunks();
                
                byte[] data = null;
                
                if(chunks.size() == 2 && chunks.get(0).equalsOpCode(OP_RETURN)){
                    data = chunks.get(1).data;
                }
                
                if(protocolService.isIoTProtocol(data) && blockIndex >= IOTBlockHeightTestnet){
                    Message message = messageFactory.get(data);
                    
                    com.tilepay.domain.entity.Transaction transaction = aTransaction().setHash(btcTransaction.getHashAsString()).setMessage(
                            message).setData(data).build();
                    
                    return transaction;
                }
            }
            
            if (sentToMultiSig) {
                List<ScriptChunk> chunks = output.getScriptPubKey().getChunks();

                byte[] data = null;

                if (chunks.size() == 5 && chunks.get(0).equalsOpCode(OP_1) && chunks.get(3).equalsOpCode(OP_2) && chunks.get(4).equalsOpCode(OP_CHECKMULTISIG)) {
                    data = chunks.get(2).data;
                }

                //TODO: 25.12.2014 Andrei Sljusar: ProtocolFactory (CNTRPRTY or TILECOIN)
                if (protocolService.isTilecoinProtocol(data)) {
                    TransactionOutput destinationOutput = btcTransaction.getOutput(0);
                    Message message = messageFactory.get(data);
                    com.tilepay.domain.entity.Transaction transaction = aTransaction().setHash(btcTransaction.getHashAsString()).setDestination(getDestinationAddress(destinationOutput)).setMessage(
                            message).setData(data).build();
                    //Coin btcAmount = multisigOutput.getValue();
                    //transaction.setBtcAmount(btcAmount.longValue());
                    //TODO: 25.11.2014 Andrei Sljusar: Collect all possible source addresses; ignore coinbase transactions and anything but the simplest Payâ€�toâ€�PubkeyHash inputs.
                    /*if vin.prevout.is_null():
                        raise DecodeError('coinbase transaction')*/

                    Set<String> sourceAddresses = new HashSet<>();

                    for (TransactionInput in : btcTransaction.getInputs()) {
                        //TODO: 08.12.2014 Andrei Sljusar: if coinbase return null transaction?
                        if (in.isCoinBase()) {
                            //return null;
                        }
                        sourceAddresses.add(getSourceAddress(in));
                    }
                    if (sourceAddresses.size() == 0) {
                        throw new RuntimeException("TODO: source addresses size is 0. Should be 1.");
                    } else if (sourceAddresses.size() == 1) {
                        transaction.setSource(sourceAddresses.iterator().next());
                    } else {
                        throw new RuntimeException("TODO: source addresses size is " + sourceAddresses.size() + ". Should be 1.");
                    }

                    return transaction;
                }

                //if (chunks.size() == 6 && chunks.get(0).equalsOpCode(OP_1) && chunks.get(4).equalsOpCode(OP_3) && chunks.get(5).equalsOpCode(OP_CHECKMULTISIG)) {
                /*if (protocolService.isCntrprtyProtocol(data)) {

                }*/

            }
        } 
        //TODO: 25.11.2014 Andrei Sljusar: vin_tx = bitcoin.get_raw_transaction(vin['txid'])     # Get the full transaction data for this input transaction.
        return null;
    }

    public String getDestinationAddress(TransactionOutput output) {
        Script script = output.getScriptPubKey();
        return script.getToAddress(networkParametersConfig.networkParameters()).toString();
    }

    //TODO: 25.11.2014 Andrei Sljusar: Require that all possible source addresses be the same.
    public String getSourceAddress(TransactionInput input) {
        //TODO: 27.12.2014 Andrei Sljusar:
        /*if vin.prevout.is_null():
            raise DecodeError('coinbase transaction')*/
        if (input.isCoinBase()) {
            return null;
        }
        Script script = input.getScriptSig();
        Address address = script.getFromAddress(networkParametersConfig.networkParameters());
        return address.toString();
    }

}
