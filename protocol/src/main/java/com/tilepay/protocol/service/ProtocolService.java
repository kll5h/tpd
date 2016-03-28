package com.tilepay.protocol.service;

import static java.util.Arrays.copyOfRange;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import org.bitcoinj.core.Transaction;
import org.bitcoinj.script.ScriptChunk;
import org.springframework.stereotype.Service;

import com.tilepay.domain.entity.Protocol;

@Service
public class ProtocolService {

    @Inject
    private ByteService byteService;

    @Inject
    private ARC4Service arc4Service;

    public boolean isTilecoinProtocol(byte[] data) {
        if (data == null) {
            return false;
        }
        byte[] expectedProtocol = Protocol.TILECOIN.getBytes();
        byte[] actualProtocol = getProtocolPrefix(data, expectedProtocol.length);
        return Arrays.equals(expectedProtocol, actualProtocol);
    }

    public boolean isCntrprtyProtocol(byte[] data) {
        if (data == null) {
            return false;
        }

        if (data.length > 9) {
            if (Arrays.equals(Protocol.CNTRPRTY.getBytes(), copyOfRange(data, 1, 9))) {
                return true;
            }
        }
        return false;
    }
    
    public boolean isIoTProtocol(byte[] data){
    	if(data == null){
    		return false;
    	}
    	byte[] expectedProtocol = Protocol.IOTC.getBytes();
    	byte[] actualProtocol = getProtocolPrefix(data, expectedProtocol.length);
    	return Arrays.equals(expectedProtocol, actualProtocol);
    }

    public byte[] getProtocolPrefix(byte[] data, int length) {
        //TODO: 08.12.2014 Andrei Sljusar: till prefix length
        return Arrays.copyOfRange(data, 0, length);
    }

    public byte[] extract(Transaction tx, List<ScriptChunk> chunks) {
        String parentTxHash = getParentTxHash(tx);
        byte[] data = byteService.toByteArray(chunks.get(1).data, chunks.get(2).data);
        data = arc4Service.decrypt(parentTxHash, data);
        return data;
    }

    public String getParentTxHash(Transaction tx) {
        String outpoint = tx.getInput(0).getOutpoint().toString();
        int pos = outpoint.lastIndexOf(":");
        return outpoint.substring(0, pos);
    }

}
