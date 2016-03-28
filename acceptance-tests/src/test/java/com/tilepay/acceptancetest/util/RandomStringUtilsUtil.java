package com.tilepay.acceptancetest.util;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

public class RandomStringUtilsUtil {

    public static String randomLowercaseString(int chars) {
        return randomAlphabetic(chars).toLowerCase();
    }
}
