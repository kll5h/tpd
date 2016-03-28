package com.tilepay.upholdclient.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("unittest")
@Configuration
public class UpholdUnitTestConfig extends UpholdConfig {

	@Override
	public String getServerUrl() {
		return "https://sandbox.uphold.com/";
	}
	
    @Override
    public String getApiUrl() {
        return "https://api-sandbox.uphold.com/";
    }

}
