package com.tilepay.upholdclient.model;

import java.util.List;

public class UpholdJsonModel<T> {

    private Boolean successful;
    private Object error;
    private List<DataTableFieldError> fieldErrors;
    private Object data;

    public Boolean getSuccessful() {
        return successful;
    }

    public void setSuccessful(Boolean successful) {
        this.successful = successful;
    }

    public Object getError() {
        return error;
    }

    public void setError(Object error) {
        this.error = error;
    }

    public List<DataTableFieldError> getFieldErrors() {
        return fieldErrors;
    }

    public void setFieldErrors(List<DataTableFieldError> fieldErrors) {
        this.fieldErrors = fieldErrors;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

}
