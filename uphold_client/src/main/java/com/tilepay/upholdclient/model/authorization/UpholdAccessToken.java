package com.tilepay.upholdclient.model.authorization;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UpholdAccessToken implements Serializable {

    private static final long serialVersionUID = 1026139328701030329L;

    @JsonProperty("access_token")
    private String accessToken;
    
    @JsonProperty("expires_in")
    private Integer expiresIn;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Integer getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Integer expiresIn) {
        this.expiresIn = expiresIn;
    }

}
