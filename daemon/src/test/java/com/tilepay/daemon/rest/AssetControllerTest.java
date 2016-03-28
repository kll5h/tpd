package com.tilepay.daemon.rest;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.util.MatcherAssertionErrors.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;

import com.tilepay.daemon.Daemon;
import com.tilepay.domain.entity.Asset;
import com.tilepay.domain.entity.AssetBuilder;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Daemon.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
public class AssetControllerTest {

    @Value("${local.server.port}")
    private int port;

    @Test
    public void saveAsset() throws Exception {
        Asset asset = AssetBuilder.anAsset().setName("XXX").setDivisible(true).setTilecoinProtocol().build();
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.postForEntity(applicationUrl() + "saveAsset", asset, String.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
    }

    @Test
    public void getBalances() {
        RestTemplate rt = new RestTemplate();
        ResponseEntity<List> response = rt.getForEntity(applicationUrl() + "getBalances/123456789", List.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
    }

    @Test
    public void getAsset() {
        RestTemplate rt = new RestTemplate();
        ResponseEntity<Asset> response = rt.getForEntity(applicationUrl() + "getAsset/AAAA", Asset.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
    }

    protected String applicationUrl() {
        return "http://localhost:" + port + "/";
    }
}