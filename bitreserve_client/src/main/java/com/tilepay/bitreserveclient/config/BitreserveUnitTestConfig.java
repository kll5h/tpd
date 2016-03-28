package com.tilepay.bitreserveclient.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("unittest")
@Configuration
public class BitreserveUnitTestConfig extends BitreserveConfig {

	@Override
	public String getServerUrl() {
		return "https://sandbox.bitreserve.org/";
	}
	
    @Override
    public String getApiUrl() {
        return "https://api-sandbox.bitreserve.org/";
    }

}
