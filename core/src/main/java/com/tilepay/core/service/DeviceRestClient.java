package com.tilepay.core.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class DeviceRestClient {

    public String getRegistrationToken(String address) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.getForEntity("http://" + address + ":8080/register", String.class);
        return responseEntity.getBody();
    }
    
    public String getData(String address) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.getForEntity("http://" + address + ":8080/data", String.class);
        return responseEntity.getBody();
    }

}