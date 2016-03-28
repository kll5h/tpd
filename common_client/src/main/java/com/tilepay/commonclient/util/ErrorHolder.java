package com.tilepay.commonclient.util;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

public class ErrorHolder {

    private HttpStatus statusCode;

    private String statusText;

    private String responseBody;

    private HttpHeaders responseHeaders;

    public ErrorHolder(HttpStatus statusCode, String statusText, String responseBody) {
        this.statusCode = statusCode;
        this.statusText = statusText;
        this.responseBody = responseBody;
    }

    public ErrorHolder(String statusText) {
        this.statusText = statusText;
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(HttpStatus statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusText() {
        return statusText;
    }

    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }

    public String getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(String responseBody) {
        this.responseBody = responseBody;
    }

    public HttpHeaders getResponseHeaders() {
        return responseHeaders;
    }

    public void setResponseHeaders(HttpHeaders responseHeaders) {
        this.responseHeaders = responseHeaders;
    }

    public static ErrorHolder build(Exception exception) {
        if (exception instanceof HttpServerErrorException) {
            HttpServerErrorException e = (HttpServerErrorException) exception;
            return new ErrorHolder(e.getStatusCode(), e.getStatusText(), e.getResponseBodyAsString());
        }

        if (exception instanceof HttpClientErrorException) {
            HttpClientErrorException e = (HttpClientErrorException) exception;
            return new ErrorHolder(e.getStatusCode(), e.getStatusText(), e.getResponseBodyAsString());
        }

        return new ErrorHolder(exception.getMessage());
    }
}