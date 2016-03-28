package com.tilepay.bitreserveclient.model.card;

import java.io.Serializable;

public class Settings implements Serializable {

    private static final long serialVersionUID = -7957040825940014885L;

    private Integer position;
    private Boolean starred;

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Boolean getStarred() {
        return starred;
    }

    public void setStarred(Boolean starred) {
        this.starred = starred;
    }
}
