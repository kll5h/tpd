package com.tilepay.web.controller.profile.company;

import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import static com.tilepay.core.constant.FieldSize.MAX_NAME;
import static com.tilepay.core.constant.FieldSize.MAX_STRING;
import static com.tilepay.core.constant.RegExpPattern.NAME;
import static com.tilepay.core.constant.RegExpPattern.PHONE_NUMBER;


public class CompanyContact {

    @Pattern(regexp = NAME)
    @Size(max = MAX_NAME)
	private String firstName;

    @Pattern(regexp = NAME)
    @Size(max = MAX_NAME)
	private String lastName;
	
	@Size(max = MAX_STRING)
	@Email
	private String email;
	
	@Pattern(regexp = PHONE_NUMBER)
    @Size(max = MAX_STRING)
	private String phone;

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	
}
