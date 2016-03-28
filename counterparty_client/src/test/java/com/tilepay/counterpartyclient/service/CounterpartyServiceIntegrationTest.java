package com.tilepay.counterpartyclient.service;

import static com.tilepay.counterpartyclient.service.PayloadBuilder.aPayload;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.google.gson.reflect.TypeToken;
import com.tilepay.counterpartyclient.config.CounterpartyClientConfig;
import com.tilepay.counterpartyclient.model.AssetInfo;
import com.tilepay.counterpartyclient.model.CounterpartyBalance;
import com.tilepay.counterpartyclient.model.CounterpartyRawTransaction;
import com.tilepay.counterpartyclient.model.CounterpartySend;
import com.tilepay.counterpartyclient.model.CounterpartyStatus;
import java.math.BigDecimal;
import java.util.List;
import javax.inject.Inject;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(classes = CounterpartyClientConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("testnet")
public class CounterpartyServiceIntegrationTest {

    @Inject
    private CounterpartyService counterpartyService;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void getBalancesByAddress() {
        List<CounterpartyBalance> counterpartyBalances = counterpartyService.getBalancesByAddress("n3ntDScCTzJCcsgc5axtFi6xzctH9f8cuY");
        assertNotNull(counterpartyBalances);
        System.out.println("balances = " + counterpartyBalances);
    }

    @Test
    public void getBalances() {
        List<CounterpartyBalance> balances = counterpartyService.getBalances("mk3gU4wM7uxukTbf9DfFXNboQmQiSb9Qu1", "TILECOINXTC");
        assertNotNull(balances);
        System.out.println("balances = " + balances);
    }

    @Test
    public void getRawTransactions() {
        List<CounterpartyRawTransaction> rawTransactions = counterpartyService.getRawTransactions("mgovDZE3AeNY1tyyXrkWyXnK1k6ARVGDvH");
        assertNotNull(rawTransactions);
        System.out.println("rawTransactions = " + rawTransactions);
    }

    @Test
    public void getAssetInfos() {
        List<AssetInfo> asset_info = counterpartyService.getAssetInfos("BTC", "TILECOINXTC", "XCP");
        assertNotNull(asset_info);
        System.out.println("asset_info = " + asset_info);

        counterpartyService.getAssetInfos();
    }

    @Test
    public void getSupplyByAsset() {
        BigDecimal supply_info = counterpartyService.getSupplyByAsset("TILECOINXTC");
        assertNotNull(supply_info);
        System.out.println("supply_info = " + supply_info);
    }

    @Test
    public void getSends() {
        List<CounterpartySend> sends = counterpartyService.getSends("mk3gU4wM7uxukTbf9DfFXNboQmQiSb9Qu1", "mvQRQAhN2KJia8PgH1xBdmeX3FgTRTdCST");
        assertNotNull(sends);
        System.out.println("sends = " + sends);
    }

    @Test
    public void getTilecoinXAssetInfo() {
        AssetInfo assetInfo = counterpartyService.getTilecoinXAssetInfo();
        System.out.println("assetInfo = " + assetInfo);
    }

    @Test
    public void methodNotFoundCounterpartyException() {

        thrown.expect(CounterpartyErrorException.class);
        thrown.expectMessage("Error{code=-32601, message='Method not found'}");

        Payload payload = aPayload().setMethod("XXX").build();
        counterpartyService.parseResponse(payload, new TypeToken<Object>() {
        }.getType());

    }

    @Test
    public void getNotOkStatus() {
        CounterpartyStatus status = counterpartyService.getStatus("https://cw03.counterwallet.io/_t_api");
        assertEquals("NOT_OK", status.getCounterpartyd());
    }

    @Ignore
    @Test
    public void wrongCounterpartyUrl() {
        counterpartyService.getStatus("https://cw04.counterwallet.io/_t_api");
    }

    @Test
    public void shouldReturnEmptyListIfCounterpartyIsNotAvailable() {

        List<Object> response = counterpartyService.parseResponse("https://cw03.counterwallet.io/_t_api", null, new TypeToken<List<Object>>() {
        }.getType());

        assertEquals(0, response.size());
    }

}