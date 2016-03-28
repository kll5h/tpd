package com.tilepay.protocol.service;

import static java.util.Arrays.copyOfRange;
import static org.bitcoinj.script.ScriptOpCodes.OP_1;
import static org.bitcoinj.script.ScriptOpCodes.OP_CHECKMULTISIG;

import javax.inject.Inject;

import org.bitcoinj.script.Script;
import org.bitcoinj.script.ScriptBuilder;
import org.bitcoinj.script.ScriptOpCodes;
import org.springframework.stereotype.Component;

@Component
public class CounterpartyMessageScriptBuilder extends MessageScriptBuilder {

    @Inject
    private ARC4Service arc4Service;

    @Inject
    private ByteService byteService;

    @Override
    public Script buildScript(byte[] sourcePubKey, byte[] data, String inputHash) {
        arc4Service.crypt(data, inputHash);
        byte[] chunk1 = byteService.createChunkKey(copyOfRange(data, 0, 31));
        byte[] chunk2 = byteService.createChunkKey(copyOfRange(data, data.length - 31, data.length));
        return new ScriptBuilder().op(OP_1).data(chunk1).data(chunk2).data(sourcePubKey).op(ScriptOpCodes.OP_3).op(OP_CHECKMULTISIG).build();
    }
}
