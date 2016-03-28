package com.tilepay.counterpartyclient.model;

public class Filter {
    private String field;
    private String op;
    private String value;

    public Filter() {
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getOp() {
        return op;
    }

    public void setOp(String operator) {
        this.op = operator;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
    
    
}