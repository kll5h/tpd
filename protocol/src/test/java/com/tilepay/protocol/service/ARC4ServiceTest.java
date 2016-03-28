package com.tilepay.protocol.service;

import static org.junit.Assert.assertArrayEquals;

import org.junit.Test;

public class ARC4ServiceTest {

    private ARC4Service arc4Service = new ARC4Service();

    @Test
    public void decrypt() {
        byte[] data = new byte[] { -9, -54, -73, -57, 33, -28, 52, 61, 73, -119, -94, -110, -71, 5, 25, 78, -10, -89, 61, -100, -13, -108, 127, -64, 124, 85, 75, -38, -107, 66, -104, -93, -72, -45,
                -107, 101, -71, 106, -93, 12, -92, 106, 105, -2, -123, 97, -90, -58, 73, -99, -2, 37, -50, 28, -103,
                15, 49, 82, -66, -57, 77, 124 };
        String key = "b86b3ba98acf5609c3a5679efab3fd6b8f805369c6cee2ebc88549ba091da2d0";

        byte[] actual = arc4Service.decrypt(key, data);

        byte[] expected = { 28, 67, 78, 84, 82, 80, 82, 84, 89, 0, 0, 0, 0, 0, 0, 0, 0, 0, 83, -35, 116, 0, 0, 0, 0, 11, -21, -62, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };

        assertArrayEquals(expected, actual);

    }

    @Test
    public void crypt() {
        byte[] actual = { 28, 67, 78, 84, 82, 80, 82, 84, 89, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, -122, -96, 1, -122 };
        byte[] expected = { -62, 93, -83, -85, -66, 43, 51, 113, 119, -19, 78, -43, 127, 69, 77, 19, -37, -124, 75, -95, -55, 114, 83, -58, 22, 14, -99, 39, -49, 80, -23 };
        String hash = "4aa7ee9c260f09792f122efd1bb432bbe945140817dc145969eea9495eca731a";

        arc4Service.crypt(actual, hash);

        assertArrayEquals(expected, actual);
    }
}