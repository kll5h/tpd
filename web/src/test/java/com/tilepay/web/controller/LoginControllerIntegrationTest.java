package com.tilepay.web.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;

@ActiveProfiles({ "unittest", "local-testnet" })
public class LoginControllerIntegrationTest extends AbstractControllerTest {

    @Test
    public void index() throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(applicationUrl(), String.class);
        System.out.println(responseEntity);

        assertEquals(200, responseEntity.getStatusCode().value());
        assertTrue(responseEntity.getBody().contains("Running Unittest"));

    }
}