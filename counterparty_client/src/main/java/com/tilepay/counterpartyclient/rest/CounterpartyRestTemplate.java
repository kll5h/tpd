package com.tilepay.counterpartyclient.rest;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class CounterpartyRestTemplate extends RestTemplate {

    @Inject
    private CounterpartyErrorHandler counterpartyErrorHandler;

    @PostConstruct
    public void postConstruct() {
        setErrorHandler(counterpartyErrorHandler);
    }
}
