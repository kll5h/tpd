package com.tilepay.upholdclient.model;

import org.springframework.validation.FieldError;

public class DataTableFieldError {

    private String name;
    private String status;

    public DataTableFieldError (String name, String status) {
    	this.name = name;
    	this.status = status;
    }
    
    public DataTableFieldError (FieldError fieldError) {
    	this.name = fieldError.getField();
    	this.status = fieldError.getDefaultMessage();
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
