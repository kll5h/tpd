package com.tilepay.bitreserveclient.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile(value = { "default", "testnet" })
@Configuration
public class BitreserveTestNetConfig extends BitreserveConfig {

	@Override
	public String getServerUrl() {
		return "https://sandbox.bitreserve.org/";
	}
	
    @Override
    public String getApiUrl() {
        return "https://api-sandbox.bitreserve.org/";
    }

}
