package com.tilepay.web.controller.profile.company;

import com.tilepay.core.model.Country;
import com.tilepay.core.model.PresenceType;

import javax.validation.constraints.Size;

import static com.tilepay.core.constant.FieldSize.*;

public class CompanyPublicDetails {

    @Size(max = MAX_STRING)
	private String companyName;

    @Size(max = MAX_STRING)
	private String address;

    @Size(max = MAX_CITY)
	private String city;

    @Size(max = MAX_STATE)
	private String state;
    
    @Size(max = MAX_ZIP)
	private String zip;
	
	private Country country;
	
	private PresenceType presenceType;

    @Size(max = MAX_CAPTION)
	private String caption;
	
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public PresenceType getPresenceType() {
		return presenceType;
	}

	public void setPresenceType(PresenceType presence) {
		this.presenceType = presence;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}
}
