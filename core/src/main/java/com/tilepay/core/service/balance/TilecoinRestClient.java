package com.tilepay.core.service.balance;

import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.tilepay.core.config.TilecoinRestApiConfig;
import com.tilepay.domain.entity.Asset;
import com.tilepay.domain.entity.Balance;
import com.tilepay.domain.entity.Protocol;
import com.tilepay.domain.entity.Transaction;

@Service
public class TilecoinRestClient {

    @Inject
    private TilecoinRestApiConfig tilecoinRestApiConfig;

    public List<Balance> getBalances(String address) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Balance[]> responseEntity = restTemplate.getForEntity(tilecoinRestApiConfig.getTilecoinRestApiUrl() + "getBalances/" + address, Balance[].class);
        return new ArrayList<>(asList(responseEntity.getBody()));
    }

    public List<Transaction> getIssuanceTransactions(String source) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Transaction[]> responseEntity = restTemplate.getForEntity(tilecoinRestApiConfig.getTilecoinRestApiUrl() + "getIssuanceTransactions/" + source, Transaction[].class);
        return new ArrayList<>(asList(responseEntity.getBody()));
    }

    public Asset getAssetByName(String assetName) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Asset> responseEntity = restTemplate.getForEntity(tilecoinRestApiConfig.getTilecoinRestApiUrl() + "getAsset/" + assetName, Asset.class);
        return responseEntity.getBody();
    }

    public void saveAsset(Asset asset) {
        asset.setProtocol(Protocol.TILECOIN.name());
        RestTemplate rt = new RestTemplate();
        //TODO: 06.01.2015 Andrei Sljusar: handle response
        ResponseEntity<String> response = rt.postForEntity(tilecoinRestApiConfig.getTilecoinRestApiUrl() + "saveAsset", asset, String.class);
        String body = response.getBody();
    }
    
    public void saveAssetWithBalance(Balance balance) {
    	balance.getAsset().setProtocol(Protocol.TILECOIN.name());
        RestTemplate rt = new RestTemplate();
        //TODO: 30.06.2015 xuht: handle response
        ResponseEntity<String> response = rt.postForEntity(tilecoinRestApiConfig.getTilecoinRestApiUrl() + "saveAssetWithBalance", balance, String.class);
        String body = response.getBody();
    }

    public Balance getBalance(String address, Asset asset) {

        List<Balance> balances = getBalances(address);

        for (Balance balance : balances) {
            if (balance.getAsset().getName().equals(asset.getName())) {
                return balance;
            }
        }

        throw new IllegalArgumentException("No Balance for address: " + address + " and asset: " + asset.getName());
    }

}
