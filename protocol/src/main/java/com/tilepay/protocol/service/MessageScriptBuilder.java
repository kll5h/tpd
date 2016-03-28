package com.tilepay.protocol.service;

import org.bitcoinj.script.Script;

public abstract class MessageScriptBuilder {

    public abstract Script buildScript(byte[] sourcePubKey, byte[] data, String inputHash);

}
