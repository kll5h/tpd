package com.tilepay.web.uphold.form;

import java.io.Serializable;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

public class UpholdCardForm implements Serializable {

    private static final long serialVersionUID = 7581122228289061655L;

    private String id;

    @NotBlank
    @Size(min = 1, max = 140)
    private String label;

    private Integer position;

    private Boolean starred;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

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
