package com.tilepay.core.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;

import static javax.persistence.CascadeType.ALL;

import java.io.Serializable;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class CompanyAccount extends Account implements Serializable {

    private static final long serialVersionUID = -3626156899751089908L;
    
    @OneToOne(cascade = ALL)
	@JoinColumn(name = "company_contact_details_id")
	private CompanyContactDetails companyContactDetails = new CompanyContactDetails();

	public CompanyContactDetails getCompanyContactDetails() {
		return companyContactDetails;
	}

	public void setCompanyContactDetails(CompanyContactDetails companyContactDetails) {
		this.companyContactDetails = companyContactDetails;
	}
}
