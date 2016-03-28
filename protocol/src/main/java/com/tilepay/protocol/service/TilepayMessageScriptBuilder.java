package com.tilepay.protocol.service;

import org.bitcoinj.script.Script;
import org.bitcoinj.script.ScriptBuilder;
import org.bitcoinj.script.ScriptOpCodes;
import org.springframework.stereotype.Component;

@Component
public class TilepayMessageScriptBuilder extends MessageScriptBuilder {

    @Override
    public Script buildScript(byte[] sourcePubKey, byte[] data, String inputHash) {
        return new ScriptBuilder().op(ScriptOpCodes.OP_1).data(sourcePubKey).data(data).op(ScriptOpCodes.OP_2).op(ScriptOpCodes.OP_CHECKMULTISIG).build();
    }
}
