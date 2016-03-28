package com.tilepay.protocol.service;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.math.BigInteger;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("unittest")
public class ByteServiceTest {

    private ByteService byteService = new ByteService();

    @Test
    public void toByteArray() {
        byte[] actual = byteService.toByteArray(new BigInteger("0"));
        assertArrayEquals(new byte[] { 0, 0, 0, 0, 0, 0, 0, 0 }, actual);

        actual = byteService.toByteArray(new BigInteger("1"));
        assertArrayEquals(new byte[] { 0, 0, 0, 0, 0, 0, 0, 1 }, actual);

        actual = byteService.toByteArray(BigInteger.valueOf(Long.MAX_VALUE));
        assertArrayEquals(new byte[] { 127, -1, -1, -1, -1, -1, -1, -1 }, actual);

        actual = byteService.toByteArray(new BigInteger("95428956661682177"));
        assertArrayEquals(new byte[] { 1, 83, 8, 33, 103, 27, 16, 1 }, actual);

        actual = byteService.toByteArray(new BigInteger("18446744073709551615"));
        assertArrayEquals(new byte[] { -1, -1, -1, -1, -1, -1, -1, -1 }, actual);
    }

    /*@Test
    public void createDataChunk() {
        byte[] expected = { 28, 67, 78, 84, 82, 80, 82, 84, 89, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, -122, -96, 0, 0, 0, 0 };
        byte[] actual = byteService.createDataChunk(33, BigInteger.ONE, new BigDecimal("100000"));

        assertArrayEquals(expected, actual);
    }*/

    @Test
    public void getBytesWithoutFirstLast() {
        byte[] data = { 0, 28, 67, 78, 84, 82, 80, 82, 84, 89, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, -122, -96, 1, -122, 0 };
        byte[] expectedData = { 28, 67, 78, 84, 82, 80, 82, 84, 89, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, -122, -96, 1, -122 };
        byte[] actualData = byteService.getBytesWithoutFirstLast(data);

        assertArrayEquals(expectedData, actualData);
    }

    @Test
    public void createChunkKey() {
        byte[] chunk = { 28, 67, 78, 84, 82, 80, 82, 84, 89, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, -122, -96, 1, -122 };
        byte[] expectedChunk = { 0, 28, 67, 78, 84, 82, 80, 82, 84, 89, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, -122, -96, 1, -122, 0 };
        byte[] actualChunk = byteService.createChunkKey(chunk);

        assertArrayEquals(expectedChunk, actualChunk);
    }


    @Test
    public void getBigInteger() {
        assertEquals(101328831, byteService.getBigInteger("00000000060a27bf").intValue());
        assertEquals(3500000000L, byteService.getBigInteger("00000000d09dc300").longValue());
    }

}