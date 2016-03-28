package com.tilepay.core.model;

public class AddressBuilder {
	
	private String address;
	
	public static AddressBuilder aAddress() {
		return new AddressBuilder();
	}
	
	public AddressBuilder withAddress(String address){
		this.address = address;
		return this;
	}
	
	public Address build() {
		Address address = new Address();
		address.setAddress(this.address);
		return address;
	}
}
