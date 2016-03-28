package com.tilepay.protocol.service;

import static org.junit.Assert.assertEquals;

import java.nio.ByteBuffer;
import java.util.List;

import javax.inject.Inject;

import org.bitcoinj.script.Script;
import org.bitcoinj.script.ScriptChunk;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tilepay.protocol.config.ProtocolConfig;

@ContextConfiguration(classes = ProtocolConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class CounterpartyMessageScriptBuilderTest {

    @Inject
    private CounterpartyMessageScriptBuilder counterpartyMessageScriptBuilder;

    @Test
    public void buildScript() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(66);

        Script script = counterpartyMessageScriptBuilder.buildScript(new byte[] { 1 }, byteBuffer.array(), "4aa7ee9c260f09792f122efd1bb432bbe945140817dc145969eea9495eca731a");

        List<ScriptChunk> chunks = script.getChunks();
        assertEquals(6, chunks.size());
        assertEquals(33, chunks.get(1).data.length);
        assertEquals(33, chunks.get(2).data.length);
    }
}