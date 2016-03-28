package com.tilepay.core.model;

import org.hibernate.validator.constraints.Email;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;

import static com.tilepay.core.constant.FieldSize.*;
import static com.tilepay.core.constant.RegExpPattern.NAME;
import static com.tilepay.core.constant.RegExpPattern.PHONE_NUMBER;
import static org.springframework.format.annotation.DateTimeFormat.ISO.DATE;

public class PrivateDetails {
	
	@Size(max = MAX_STRING)
	@Email
	private String email;

	@Pattern(regexp = NAME)
	@Size(max = MAX_NAME)
	private String firstName;

	@Pattern(regexp = NAME)
    @Size(max = MAX_NAME)
	private String lastName;

	@Size(max = MAX_STRING)
	private String address;

    @Size(max = MAX_CITY)
	private String city;

    @Size(max = MAX_STATE)
	private String state;

    @Size(max = MAX_ZIP)
	private String zip;

	private Country country;

	@DateTimeFormat(iso = DATE)
	private Date dateOfBirth;
	
	@Pattern(regexp = PHONE_NUMBER)
	private String phone;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

}
