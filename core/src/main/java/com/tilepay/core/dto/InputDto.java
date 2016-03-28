package com.tilepay.core.dto;

import org.hibernate.validator.constraints.NotBlank;

public class InputDto {

	@NotBlank
	private String address;
	
	@NotBlank
	private String amount;

	@NotBlank
	private String script;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getScript() {
		return script;
	}

	public void setScript(String script) {
		this.script = script;
	}
	
}
