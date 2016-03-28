package com.tilepay.upholdclient;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.tilepay.upholdclient.config.UpholdConfig;

@Service("upholdClientFactory")
public class UpholdClientFactory {
    @Inject
    private UpholdConfig upholdConfig;

    public UpholdClient getUpholdClient(String clientId, String clientSecret, String scope) {
        return new UpholdClient(upholdConfig.getServerUrl(), upholdConfig.getApiUrl(), clientId, clientSecret, scope);
    }
}
