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
public class TilepayOPReturnMessageScriptBuilderTest {

    @Inject
    private TilepayOPReturnMessageScriptBuilder tilepayOPReturnMessageScriptBuilder;

    @Test
    public void buildScript() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(40);

        Script script = tilepayOPReturnMessageScriptBuilder.buildScript(null, byteBuffer.array(), null);

        List<ScriptChunk> chunks = script.getChunks();
        assertEquals(2, chunks.size());
        assertEquals(106, chunks.get(0).opcode);
        assertEquals(40, chunks.get(1).data.length);
    }
}