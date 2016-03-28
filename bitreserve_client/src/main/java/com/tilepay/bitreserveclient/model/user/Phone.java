package com.tilepay.bitreserveclient.model.user;

import java.io.Serializable;

public class Phone implements Serializable {

    private static final long serialVersionUID = 7443101615370256358L;

    private String id;
    private String e164Masked;
    private String internationalMasked;
    private String nationalMasked;
    private Boolean primary;
    private Boolean verified;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getE164Masked() {
        return e164Masked;
    }

    public void setE164Masked(String e164Masked) {
        this.e164Masked = e164Masked;
    }

    public String getInternationalMasked() {
        return internationalMasked;
    }

    public void setInternationalMasked(String internationalMasked) {
        this.internationalMasked = internationalMasked;
    }

    public String getNationalMasked() {
        return nationalMasked;
    }

    public void setNationalMasked(String nationalMasked) {
        this.nationalMasked = nationalMasked;
    }

    public Boolean getPrimary() {
        return primary;
    }

    public void setPrimary(Boolean primary) {
        this.primary = primary;
    }

    public Boolean getVerified() {
        return verified;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

}
