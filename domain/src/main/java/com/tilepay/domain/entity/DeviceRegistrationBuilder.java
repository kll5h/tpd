package com.tilepay.domain.entity;

public class DeviceRegistrationBuilder extends MessageBuilder<DeviceRegistrationBuilder> {

	private String registrationToken;
	
    public static DeviceRegistrationBuilder aDeviceRegistration() {
        return new DeviceRegistrationBuilder();
    }

    @Override
    public DeviceRegistration build() {
        DeviceRegistration object = new DeviceRegistration();
        object.setRegistrationToken(registrationToken);
        return object;
    }

    @Override
    protected DeviceRegistrationBuilder getThis() {
        return this;
    }

	public DeviceRegistrationBuilder setRegistrationToken(String registrationToken) {
		this.registrationToken = registrationToken;
		return this;
	}
}
