package com.tilepay.protocol.service;

import static org.bitcoinj.script.ScriptOpCodes.OP_RETURN;

import org.bitcoinj.script.Script;
import org.bitcoinj.script.ScriptBuilder;
import org.springframework.stereotype.Component;

@Component
public class TilepayOPReturnMessageScriptBuilder extends MessageScriptBuilder {

    @Override
    public Script buildScript(byte[] sourcePubKey, byte[] data, String inputHash) {
    	return new ScriptBuilder().op(OP_RETURN).data(data).build();
    }
}
