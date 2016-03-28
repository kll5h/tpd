package com.tilepay.core.constant;

public class RegExpPattern {

    public static final String NAME = "^([ \\u0451\\u0401\\u0410-\\u044f\\u00c0-\\u01ffa-zA-Z\\-])+$";
    public static final String PHONE_NUMBER = "(^$)|(^\\+?(\\d\\-?){7,20}$)";
    public static final String DOUBLE_NUMBER = "^(\\d)+([\\.||\\,](\\d){1,8})?$";
    public static final String INTEGER = "\\d+";
}
