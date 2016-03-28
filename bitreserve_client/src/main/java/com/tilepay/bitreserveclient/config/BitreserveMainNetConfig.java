package com.tilepay.bitreserveclient.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile(value = { "mainnet" })
@Configuration
public class BitreserveMainNetConfig extends BitreserveConfig {

	@Override
	public String getServerUrl() {
		return "https://bitreserve.org/";
	}
	
    @Override
    public String getApiUrl() {
        return "https://api.bitreserve.org/";
    }
    
}
