package com.tilepay.counterpartyclient.model;

public class FilterBuilder {
    private String field;
    private String op;
    private String value;

    public FilterBuilder setField(String field) {
        this.field = field;
        return this;
    }

    public FilterBuilder setOp(String operator) {
        this.op = operator;
        return this;
    }

    public FilterBuilder setValue(String value) {
        this.value = value;
        return this;
    }

    public static FilterBuilder aFilter() {
        return new FilterBuilder();
    }

    public Filter build() {
        Filter object = new Filter();
        object.setField(field);
        object.setOp(op);
        object.setValue(value);
        return object;
    }

}