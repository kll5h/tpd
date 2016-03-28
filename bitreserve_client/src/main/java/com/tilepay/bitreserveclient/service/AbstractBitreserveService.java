package com.tilepay.bitreserveclient.service;

import javax.inject.Inject;

import org.apache.http.Header;
import org.apache.http.HttpHeaders;
import org.apache.http.message.BasicHeader;

import com.google.gson.Gson;
import com.tilepay.bitreserveclient.config.BitreserveConfig;
import com.tilepay.bitreserveclient.exception.BitreserveErrorException;
import com.tilepay.bitreserveclient.util.HttpClientUtil;

public class AbstractBitreserveService {

    protected static final Gson gson = new Gson();

    protected static String HEADER = "Bearer %s";

    @Inject
    protected BitreserveConfig bitreserveConfig;

    /**
     * create a HTTP header with token
     * 
     * @param token
     * @return
     */
    protected Header CreateHeader(String token) {
        return new BasicHeader(HttpHeaders.AUTHORIZATION, String.format(HEADER, token));
    }

    /**
     * send a request with HTTP get
     * 
     * @param url
     * @param token
     * @return
     * @throws BitreserveErrorException
     */
    protected String sendGetRequest(String url, String token) throws BitreserveErrorException {
        Header httpHeader = CreateHeader(token);
        String json = HttpClientUtil.doGetWithOneHeader(url, httpHeader);
        return json;
    }

}
