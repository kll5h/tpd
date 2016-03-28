package com.tilepay.counterpartyclient.rest;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.DefaultResponseErrorHandler;

import com.google.gson.Gson;
import com.tilepay.counterpartyclient.model.CounterpartyStatus;
import com.tilepay.counterpartyclient.service.CounterpartyIsNotAvailableException;

@Component
public class CounterpartyErrorHandler extends DefaultResponseErrorHandler {

    private static final Gson gson = new Gson();

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        // TODO: Feb 3, 2015 Mait Mikkelsaar: Maybe only check success when statusCode 200?
        if (response.getRawStatusCode() == 200) {
            return false;
        } else {
            return isError(response);
        }
    }

    private boolean isError(ClientHttpResponse response) throws IOException {

        HttpStatus.Series series = null;
        try {
            series = response.getStatusCode().series();
        } catch (IllegalArgumentException e) {
            if (e.getMessage().equals("No matching constant for [525]")) {
                InputStream body = response.getBody();
                String s = IOUtils.toString(body);
                //TODO: 12.02.2015 Andrei Sljusar: returns CounterpartyError?
                //throw new CounterpartyIsNotAvailableException(s);
                throw new IOException(s);
            }
        }

        if (HttpStatus.Series.SERVER_ERROR.equals(series)) {
            InputStream body = response.getBody();
            String s = IOUtils.toString(body);
            CounterpartyStatus counterpartyStatus = gson.fromJson(s, CounterpartyStatus.class);
            counterpartyStatus.setCounterpartyd("NOT_OK");
            //throw new CounterpartyIsNotAvailableException(counterpartyStatus);
            throw new IOException(counterpartyStatus.toString());
        }

        return false;
    }

}
