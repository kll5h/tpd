package com.tilepay.counterpartyclient.config;

import java.util.Date;

import javax.inject.Inject;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;

import com.tilepay.counterpartyclient.model.CounterpartyStatus;
import com.tilepay.counterpartyclient.service.CounterpartyIsNotAvailableException;
import com.tilepay.counterpartyclient.service.CounterpartyService;

@Profile(value = { "default", "testnet" })
@Configuration
public class TestNetConfig extends CounterpartyConfig {
    
    private String[] urlStrs = new String[] {
              "https://cw01.counterwallet.io/_t_api"
            , "https://counterwallet.coindaddy.io/_t_api"
            //, "https://counterwallet-testnet.coindaddy.io/_t_api"
            //, "https://54.69.214.78/_t_api"
            //, "https://cw02.counterwallet.io/_t_api"
            };
    
    @Inject
    private CounterpartyService counterpartyService;

    private String serverUrl = null;

    @Override
    public String getServerUrl() throws CounterpartyIsNotAvailableException {
        if (serverUrl == null) {
            serverUrl = getAvailableServerUrl();
        }

        return serverUrl;
    }

    private String getAvailableServerUrl() throws CounterpartyIsNotAvailableException {
        for (String url : urlStrs) {
            //System.err.println(new Date().toString() + "  " + url);
            CounterpartyStatus counterpartyStatus = counterpartyService.getStatus(url);
            if (!"NOT_OK".equals(counterpartyStatus.getCounterpartyd())) {
                //System.err.println(url);
                return url;
            }
        }
        //System.err.println("N/A");
        throw new CounterpartyIsNotAvailableException("All server url are not available.");
    }
    
    private void detect() {
        CounterpartyStatus counterpartyStatus;
        if (serverUrl == null) {
            for (String url : urlStrs) {
                counterpartyStatus = counterpartyService.getStatus(url);
                if (!"NOT_OK".equals(counterpartyStatus.getCounterpartyd())) {
                    serverUrl = url;
                    break;
                }
            }
        } else {
            counterpartyStatus = counterpartyService.getStatus(serverUrl);
            if ("NOT_OK".equals(counterpartyStatus.getCounterpartyd())) {
                serverUrl = null;
            }
        }
    }

    @Scheduled(fixedRate = 600000)
    public void run() {
        //System.err.println(new Date().toString() + "  run start");
        detect();
        //System.err.println(new Date().toString() + "  run end");
    }
}
