package com.tilepay.bitreserveclient.model.contact;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.Size;

public class Contact implements Serializable {

    private static final long serialVersionUID = 5631112578962284047L;

    private String id;

    @Size(max = 255)
    private String firstName;

    @Size(max = 255)
    private String lastName;

    private String name;

    private List<String> addresses;

    @Size(max = 255)
    private String company;

    private List<String> emails;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<String> addresses) {
        this.addresses = addresses;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public List<String> getEmails() {
        return emails;
    }

    public void setEmails(List<String> emails) {
        this.emails = emails;
    }

}
