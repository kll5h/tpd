package com.tilepay.bitreserveclient.model.user;

import java.io.Serializable;

public class Verifications implements Serializable {

    private static final long serialVersionUID = 7243873800253823481L;

    private Identity identity;

    public Identity getIdentity() {
        return identity;
    }

    public void setIdentity(Identity identity) {
        this.identity = identity;
    }

}
