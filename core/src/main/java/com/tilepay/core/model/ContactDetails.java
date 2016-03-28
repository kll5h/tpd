package com.tilepay.core.model;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.AUTO;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class ContactDetails implements Serializable {

    private static final long serialVersionUID = 7731381640453846443L;

    @Id
	@GeneratedValue(strategy = AUTO)
	private Long id;

//	@Pattern(regexp = RegExpPattern.NAME)
	private String firstName;

//	@Pattern(regexp = RegExpPattern.NAME)
	private String lastName;

	private String userName;

//	@Pattern(regexp = RegExpPattern.PHONE_NUMBER)
	private String phone;

	@Column
//	@Pattern(regexp = RegExpPattern.EMAIL)
//	@Size(max = FieldSize.MAX_STRING)
	private String email;

	private String caption;
	
	private String address;

	private String city;

	private String state;

	private String zip;

	@OneToOne(fetch = LAZY)
	@JoinColumn(name = "country_id")
	private Country country;

	private Date dateOfBirth;

    
//	@OneToOne(fetch = LAZY)
//	@JoinColumn(name = "id", table = "contact_details")
//	private Account account;

//	public ContactDetails(Account account) {
//		this.account = account;
//	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

}
