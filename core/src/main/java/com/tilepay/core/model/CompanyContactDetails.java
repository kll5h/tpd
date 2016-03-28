package com.tilepay.core.model;

import static com.tilepay.core.constant.FieldSize.MAX_CAPTION;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.AUTO;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.Size;

@Entity
public class CompanyContactDetails implements Serializable {

    private static final long serialVersionUID = -3462459578131923207L;

    @Id
	@GeneratedValue(strategy = AUTO)
	private Long id;

	private String companyName;

	private String address;

	private String zip;

	private String city;

	private String state;

	@OneToOne(fetch = LAZY)
	@JoinColumn(name = "country_id")
	private Country country;

	private PresenceType presence;

	@Size(max = MAX_CAPTION)
	private String caption;

	private String merchantId;

	private String companyType;

	private CompanyCategory companyCategory;

	private TaxIdType taxIdType;

	private String taxId;

	private String firstName;

	private String lastName;

	private String email;

	private String phone;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getCompanyType() {
		return companyType;
	}

	public void setCompanyType(String companyType) {
		this.companyType = companyType;
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

	public PresenceType getPresence() {
		return presence;
	}

	public void setPresence(PresenceType presence) {
		this.presence = presence;
	}

	public CompanyCategory getCompanyCategory() {
		return companyCategory;
	}

	public void setCompanyCategory(CompanyCategory companyCategory) {
		this.companyCategory = companyCategory;
	}

	public TaxIdType getTaxIdType() {
		return taxIdType;
	}

	public void setTaxIdType(TaxIdType companyTaxIdType) {
		this.taxIdType = companyTaxIdType;
	}

	public String getTaxId() {
		return taxId;
	}

	public void setTaxId(String companyTaxId) {
		this.taxId = companyTaxId;
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
