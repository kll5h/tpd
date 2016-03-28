package com.tilepay.bitreserveclient.model.card;

import java.io.Serializable;

public class CardAddress implements Serializable {

    private static final long serialVersionUID = -8898117148111370327L;

    private String id;
    private String network;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNetwork() {
        return network;
    }

    public void setNetwork(String network) {
        this.network = network;
    }

}
