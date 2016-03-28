package com.tilepay.core.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;

import static javax.persistence.CascadeType.ALL;

import java.io.Serializable;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class IndividualAccount extends Account implements Serializable {

    private static final long serialVersionUID = 7844054051996655716L;
    
    @OneToOne(cascade = ALL)
	@JoinColumn(name = "contact_details_id")
	private ContactDetails contactDetails = new ContactDetails();

	public ContactDetails getContactDetails() {
		return contactDetails;
	}

	public void setContactDetails(ContactDetails contactDetails) {
		this.contactDetails = contactDetails;
	}

}