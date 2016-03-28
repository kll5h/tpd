package com.tilepay.domain.entity;

import javax.persistence.Transient;

public class DeviceRegistration extends Message {

    @Transient
    public static Integer messageId = 30;

    @Transient
    private String registrationToken;

	public String getRegistrationToken() {
		return registrationToken;
	}

	public void setRegistrationToken(String registrationToken) {
		this.registrationToken = registrationToken;
	}


    
}
