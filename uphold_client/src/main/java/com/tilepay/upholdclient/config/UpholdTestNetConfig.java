package com.tilepay.upholdclient.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile(value = { "default", "testnet" })
@Configuration
public class UpholdTestNetConfig extends UpholdConfig {

	@Override
	public String getServerUrl() {
		return "https://sandbox.uphold.com/";
	}
	
    @Override
    public String getApiUrl() {
        return "https://api-sandbox.uphold.com/";
    }

}
