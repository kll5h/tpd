package com.tilepay.counterpartyclient.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("unittest")
@Configuration
public class UnitTestConfig extends CounterpartyConfig {

    @Override
    public String getServerUrl() {
        return "https://cw01.counterwallet.io/_t_api";
    }

}
