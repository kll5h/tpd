package com.tilepay.protocol.service;

import static org.junit.Assert.assertEquals;

import java.nio.ByteBuffer;

import org.junit.Test;

public class MessageFactoryTest {

    private MessageFactory messageFactory = new MessageFactory();

    @Test
    public void getMessageId() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(33);
        byteBuffer.putInt(8, 20);

        Integer messageId = messageFactory.getMessageId(byteBuffer.array(),8);

        assertEquals(20, messageId.intValue());
    }
}