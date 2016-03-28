package com.tilepay.counterpartyclient.service;

import static org.junit.Assert.assertEquals;

import com.tilepay.counterpartyclient.config.CounterpartyClientConfig;
import com.tilepay.counterpartyclient.model.AssetInfo;
import com.tilepay.counterpartyclient.model.CounterpartyBalance;
import com.tilepay.counterpartyclient.model.CounterpartySend;
import com.tilepay.counterpartyclient.model.Filter;
import com.tilepay.counterpartyclient.model.FilterBuilder;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import javax.inject.Inject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(classes = CounterpartyClientConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class CounterpartyServiceTest {

    @Inject
    private CounterpartyService counterpartyService;

    String getNormalizedBalancesResponse =
            "[{\"owner\":true,\"address\":\"mgovDZE3AeNY1tyyXrkWyXnK1k6ARVGDvH\",\"quantity\":49999944500000000,\"normalized_quantity\":4.99999445E8,\"asset\":\"TESTAPP\"},"
                    + "{\"owner\":true,\"address\":\"mgovDZE3AeNY1tyyXrkWyXnK1k6ARVGDvH\",\"quantity\":100000000000000000,\"normalized_quantity\":1.0E9,\"asset\":\"TILECOIN\"},"
                    + "{\"owner\":false,\"address\":\"mgovDZE3AeNY1tyyXrkWyXnK1k6ARVGDvH\",\"quantity\":6998503118,\"normalized_quantity\":69.98503118,\"asset\":\"XCP\"}]";

    @Test
    public void getBalances() {
        List<CounterpartyBalance> counterpartyBalances = counterpartyService.getBalances(getNormalizedBalancesResponse);
        assertEquals(3, counterpartyBalances.size());

        assertBalance(counterpartyBalances.get(0), "mgovDZE3AeNY1tyyXrkWyXnK1k6ARVGDvH", BigInteger.valueOf(49999944500000000L), "TESTAPP");
        assertBalance(counterpartyBalances.get(1), "mgovDZE3AeNY1tyyXrkWyXnK1k6ARVGDvH", BigInteger.valueOf(100000000000000000L), "TILECOIN");
        assertBalance(counterpartyBalances.get(2), "mgovDZE3AeNY1tyyXrkWyXnK1k6ARVGDvH", BigInteger.valueOf(6998503118L), "XCP");
    }

    @Test
    public void parseAssetInfos() {
        List<AssetInfo> assets = counterpartyService.parseAssetInfos(
                "[{\"owner\":\"msZETr3jAoa2HtrYBxyS6s4MgxDxa7nbqi\",\"call_date\":0,\"callable\":false,\"divisible\":true,\"call_price\":0.0,\"description\":\"100000000\",\"locked\":false,\"asset\":\"TILECOINXTC\",\"supply\":10000000000000000,\"issuer\":\"msZETr3jAoa2HtrYBxyS6s4MgxDxa7nbqi\"},"
                        + "{\"owner\":null,\"call_date\":null,\"callable\":false,\"divisible\":true,\"call_price\":null,\"description\":\"\",\"locked\":false,\"asset\":\"XCP\",\"supply\":4366780407082,\"issuer\":null}]");

        assertEquals(2, assets.size());
        assertEquals("TILECOINXTC", assets.get(0).getAsset());
        assertEquals(true, assets.get(0).getDivisible());
    }

    @Test
    public void parseSends() {
        List<CounterpartySend> sends = counterpartyService.parseSends(
                "[{\"quantity\": 100000000000,  \"destination\": \"mq373re9nGVN6Dwaynpo8KMdFMotUcuooa\", \"tx_hash\": \"f0571cfda320f1d663ad4721f03caaf8958e2d4fc71373e3c524984df7ed7c19\", \"block_index\": 312212, \"source\": \"msZETr3jAoa2HtrYBxyS6s4MgxDxa7nbqi\", \"tx_index\": 36, \"asset\": \"TILECOINXTC\", \"status\": \"valid\"}]");
        assertEquals(1, sends.size());
        assertEquals(new BigDecimal("100000000000"), sends.get(0).getQuantity());
    }

    //TODO: 22.01.2015 Andrei Sljusar: move to FilterBuilderTest
    @Test
    public void filterCreation() {
        String field = "tx_hash";
        String op = "==";
        String value = "123hash";

        Filter filter = FilterBuilder.aFilter().setField(field).setOp(op).setValue(value).build();
        assertEquals(field, filter.getField());
        assertEquals(op, filter.getOp());
        assertEquals(value, filter.getValue());
    }

    private void assertBalance(CounterpartyBalance counterpartyBalance, String address, BigInteger quantity, String asset) {
        assertEquals(quantity, counterpartyBalance.getQuantity().toBigInteger());
        assertEquals(address, counterpartyBalance.getAddress());
        assertEquals(asset, counterpartyBalance.getAsset());
    }

}