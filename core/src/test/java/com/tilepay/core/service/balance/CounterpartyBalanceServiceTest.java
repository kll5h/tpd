package com.tilepay.core.service.balance;

import static com.tilepay.core.model.AddressBuilder.aAddress;
import static com.tilepay.core.model.AssetTransactionBuilder.aAssetTransaction;
import static com.tilepay.counterpartyclient.model.CounterpartyBalanceBuilder.aCounterpartyBalance;
import static com.tilepay.counterpartyclient.model.CounterpartyRawTransactionBuilder.aCounterpartyRawTransaction;
import static com.tilepay.domain.entity.AssetBuilder.anAsset;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import com.tilepay.core.model.Address;
import com.tilepay.core.model.AssetTransaction;
import com.tilepay.core.repository.AssetTransactionRepository;
import com.tilepay.core.service.AddressService;
import com.tilepay.core.service.AssetConverter;
import com.tilepay.core.service.AssetQuantityConversionService;
import com.tilepay.core.service.AssetTransactionService;
import com.tilepay.counterpartyclient.model.AssetInfo;
import com.tilepay.counterpartyclient.model.CounterpartyBalance;
import com.tilepay.counterpartyclient.model.CounterpartyRawTransaction;
import com.tilepay.counterpartyclient.service.CounterpartyService;
import com.tilepay.domain.entity.Asset;
import com.tilepay.domain.entity.CurrencyEnum;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CounterpartyBalanceServiceTest {

    @InjectMocks
    private CounterpartyBalanceService counterpartyBalanceService;

    @Mock
    private AssetQuantityConversionService assetQuantityConversionService;

    @Mock
    private AssetTransactionService assetTransactionService;

    @Mock
    private AssetTransactionRepository transactionRepository;

    @Mock
    private AddressService addressService;

    @Mock
    private CounterpartyService counterpartyService;

    @Mock
    private AssetConverter assetConverter;

    private CounterpartyBalance customCounterpartyBalance;

    @Before
    public void setup() {
        customCounterpartyBalance = aCounterpartyBalance().setAsset("NEWCOIN").setQuantity(BigInteger.valueOf(100000000L)).build();
    }

    @Test
    public void setEstimatedBalances() {
        CounterpartyBalance counterpartyBalance = aCounterpartyBalance().setAsset("NEWCOIN").setQuantity(new BigInteger("100000000")).build();
        List<CounterpartyBalance> counterpartyBalances = Arrays.asList(counterpartyBalance);

        when(addressService.findOneByAddress(customCounterpartyBalance.getAddress())).thenReturn(aAddress().withAddress("address123").build());
        when(assetConverter.getAsset("NEWCOIN")).thenReturn(anAsset().setName("NEWCOIN").build());

        counterpartyBalanceService.setEstimatedBalances(counterpartyBalances);
        assertEquals(100000000, counterpartyBalances.get(0).getEstimatedQuantity());
    }

    @Test
    public void getEstimatedBalance() {
        Address address = aAddress().withAddress("address123").build();
        customCounterpartyBalance.setAddress(address.getAddress());

        AssetTransaction transaction = aAssetTransaction().withAddress(address).withHash("hash123").withAmount(new BigDecimal("100000")).build();
        List<AssetTransaction> TxList = asList(transaction);
        List<CounterpartyRawTransaction> rawTxList = new ArrayList<>();

        Asset asset = anAsset().setName(customCounterpartyBalance.getAsset()).setDivisible(false).build();

        when(addressService.findOneByAddress(customCounterpartyBalance.getAddress())).thenReturn(address);
        when(assetConverter.getAsset("NEWCOIN")).thenReturn(asset);
        when(assetTransactionService.findAllWhereAddressAndCurrency(address, customCounterpartyBalance.getAsset())).thenReturn(TxList);
        when(counterpartyService.getRawTransactions(address.getAddress())).thenReturn(rawTxList);
        when(assetQuantityConversionService.formatAsBigInteger(asset, transaction.getAmount().toPlainString())).thenReturn(new BigInteger("100000"));

        long estimatedBalance = counterpartyBalanceService.getEstimatedBalance(customCounterpartyBalance);

        assertEquals(99900000, estimatedBalance);

        rawTxList = asList(aCounterpartyRawTransaction().withSource(address.getAddress()).withTxHash("hash123").build());

        when(counterpartyService.getRawTransactions(address.getAddress())).thenReturn(rawTxList);
        estimatedBalance = counterpartyBalanceService.getEstimatedBalance(customCounterpartyBalance);

        assertEquals(100000000, estimatedBalance);
    }

    @Test
    public void getBalancesWithTilecoinX() {

        Mockito.when(counterpartyService.getTilecoinXAssetInfo()).thenReturn(new AssetInfo());

        List<CounterpartyBalance> counterpartyBalances = counterpartyBalanceService.getBalancesWithTilecoinX(new ArrayList<>(), "12345");

        assertEquals(1, counterpartyBalances.size());

        assertBalance(counterpartyBalances.get(0), "12345", BigInteger.ZERO, CurrencyEnum.TILECOINXTC.name());
    }

    @Test
    public void getBalancesWithoutTilecoinX() {
        List<CounterpartyBalance> counterpartyBalances = counterpartyBalanceService.getBalancesWithTilecoinX(new ArrayList<>(), "12345");

        assertEquals(0, counterpartyBalances.size());
    }

    @Test
    public void getBalancesWithTilecoinX1() {
        List<CounterpartyBalance> counterpartyBalances = new ArrayList<>(asList(
                aCounterpartyBalance().setAsset("XXX").build(),
                aCounterpartyBalance().setAsset(CurrencyEnum.TILECOINXTC.name()).build(),
                aCounterpartyBalance().setAsset("YYY").build()
        ));

        Mockito.when(counterpartyService.getTilecoinXAssetInfo()).thenReturn(new AssetInfo());

        List<CounterpartyBalance> balances = counterpartyBalanceService.getBalancesWithTilecoinX(counterpartyBalances, "12345");
        assertEquals(3, balances.size());

        assertBalance(balances.get(0), null, BigInteger.ZERO, CurrencyEnum.TILECOINXTC.name());
        assertBalance(balances.get(1), null, BigInteger.ZERO, "XXX");
        assertBalance(balances.get(2), null, BigInteger.ZERO, "YYY");
    }

    @Test
    public void getCurrencyBalance() {
        Asset currency = anAsset().setName("TESTAPP").build();

        CounterpartyBalance balance = new CounterpartyBalance();
        balance.setAsset("TESTAPP");

        when(counterpartyService.getBalancesByAddress("mgovDZE3AeNY1tyyXrkWyXnK1k6ARVGDvH")).thenReturn(new ArrayList<>(asList(balance)));
        CounterpartyBalance currencyBalance = counterpartyBalanceService.getCurrencyBalance("mgovDZE3AeNY1tyyXrkWyXnK1k6ARVGDvH", currency);
        assertEquals("TESTAPP", currencyBalance.getAsset());
    }

    // TODO: 29.12.2014 Andrei Sljusar: copy-paste from CounterpartyServiceTest
    private void assertBalance(CounterpartyBalance counterpartyBalance, String address, BigInteger quantity, String asset) {
        assertEquals(quantity, counterpartyBalance.getQuantity().toBigInteger());
        assertEquals(address, counterpartyBalance.getAddress());
        assertEquals(asset, counterpartyBalance.getAsset());
    }

}
