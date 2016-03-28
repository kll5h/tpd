package com.tilepay.upholdclient.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile(value = { "mainnet" })
@Configuration
public class UpholdMainNetConfig extends UpholdConfig {

	@Override
	public String getServerUrl() {
		return "https://uphold.com/";
	}
	
    @Override
    public String getApiUrl() {
        return "https://api.uphold.com/";
    }
    
}
