package com.tilepay.counterpartyclient.config;

import com.tilepay.counterpartyclient.service.CounterpartyIsNotAvailableException;

public abstract class CounterpartyConfig {
    public abstract String getServerUrl() throws CounterpartyIsNotAvailableException;
}
