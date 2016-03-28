package com.tilepay.web.util;

public class StringUtils {
    public static String stringOf(String s, int repeatCount) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < repeatCount; i++) {
            sb.append(s);
        }
        return sb.toString();
    }
}
