package com.tilepay.web.service;

import static com.tilepay.core.dto.TransactionDTOBuilder.aTransaction;
import static com.tilepay.domain.entity.AssetBuilder.anAsset;
import static com.tilepay.domain.entity.CurrencyEnum.TILECOINXTC;
import static org.hamcrest.Matchers.hasItemInArray;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import com.tilepay.core.dto.TransactionDto;
import com.tilepay.core.service.AssetQuantityConversionService;
import com.tilepay.core.service.BitcoinService;
import com.tilepay.core.service.balance.BalanceServiceFactory;
import com.tilepay.core.service.balance.TilecoinBalanceService;
import com.tilepay.core.service.balance.TilecoinRestClient;
import com.tilepay.domain.entity.Asset;
import com.tilepay.domain.entity.CurrencyEnum;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collections;
import java.util.HashMap;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.MapBindingResult;

@RunWith(MockitoJUnitRunner.class)
@ActiveProfiles("unittest")
public class TransactionValidatorTest {

    @Spy
    private BalanceValidator balanceValidator;

    @Spy
    private ValidationService validationService;

    @Spy
    private AssetQuantityConversionService assetQuantityConversionService;

    @Mock
    private TilecoinRestClient tilecoinRestClient;

    @Mock
    private BitcoinService bitcoinService;

    @Mock
    private BalanceServiceFactory balanceServiceFactory;

    @Mock
    private TilecoinBalanceService tilecoinBalanceService;

    @InjectMocks
    private TransactionValidator transactionValidator;

    @Test
    public void minimumAssetAmountError() {

        Asset asset = anAsset().setName(TILECOINXTC.name()).setDivisible(true).build();
        TransactionDto transaction = aTransaction().setAsset(asset).setMinersFee("0.0001").withAmount("0").build();

        BindingResult result = new MapBindingResult(Collections.emptyMap(), "");

        transactionValidator.validateAmount(transaction, null, result);

        assertEquals(1, result.getErrorCount());

        assertFieldError(result, "amount", "transaction.minimum.asset.amount");
    }

    @Test
    public void indivisibleAssetAmountError() {
        Asset asset = anAsset().setName("ABCD").setTilecoinProtocol().setDivisible(false).build();
        TransactionDto transaction = aTransaction().setAsset(asset).setMinersFee("0.0001").withAmount("1.2").setSource("ms3NtGxiDQV75kgr5cMojtYAUVSp7kZ83n").build();
        BindingResult result = new MapBindingResult(new HashMap<>(), "");

        transactionValidator.validateAmount(transaction, null, result);

        assertEquals(1, result.getErrorCount());

        assertFieldError(result, "amount", "transaction.amount.indivisible");
    }

    @Test
    public void minimumTileCoinAssetAmountError() {

        Asset asset = anAsset().setName("TEST").setDivisible(false).setTilecoinProtocol().build();
        TransactionDto transaction = aTransaction().setAsset(asset).setMinersFee("0.0001").withAmount("11").setSource("ms3NtGxiDQV75kgr5cMojtYAUVSp7kZ83n").build();

        when(bitcoinService.isValidAddress("ms3NtGxiDQV75kgr5cMojtYAUVSp7kZ83n")).thenReturn(true);
        when(balanceServiceFactory.getBalanceService(asset)).thenReturn(tilecoinBalanceService);
        when(tilecoinBalanceService.getEstimatedBalance("ms3NtGxiDQV75kgr5cMojtYAUVSp7kZ83n", asset)).thenReturn(new BigInteger("5"));

        BindingResult result = new MapBindingResult(Collections.emptyMap(), "");

        transactionValidator.validateAmount(transaction, 200.0, result);

        assertEquals(1, result.getErrorCount());

        assertFieldError(result, "amount", "transaction.asset.amount.exceeds.amount");
    }

    @Test
    public void minimumBtcAmountError() {

        TransactionDto transaction = aTransaction().withCurrency(CurrencyEnum.BTC.name()).withAmount("0.00000546").build();
        transaction.getAsset().setDivisible(true);
        BindingResult result = new MapBindingResult(new HashMap<>(), "");

        transactionValidator.validateAmount(transaction, null, result);

        assertEquals(1, result.getErrorCount());

        assertFieldError(result, "amount", "transaction.minimum.BTC.amount");
    }

    @Test
    public void validateAmount() {
        Asset asset = anAsset().setName(CurrencyEnum.BTC.name()).setDivisible(true).build();
        TransactionDto transaction = aTransaction().setAsset(asset).setMinersFee("0.0001").withAmount("1.2").build();
        BindingResult result = new MapBindingResult(new HashMap<>(), "");

        transactionValidator.validateAmount(transaction, 1.1, result);

        assertEquals(1, result.getErrorCount());

        assertFieldError(result, "amount", "transactionForm.amount.exceeding");
    }

    @Test
    public void validateAmountWithComma() {
        Asset asset = anAsset().setName(CurrencyEnum.BTC.name()).setDivisible(true).build();
        TransactionDto transaction = aTransaction().setAsset(asset).setMinersFee("0.0001").withAmount("1,2").build();
        BindingResult result = new MapBindingResult(new HashMap<>(), "");

        transactionValidator.validateAmount(transaction, 1.3, result);

        assertEquals(0, result.getErrorCount());
    }

    @Test
    public void validateAmountEmptyCurrency() {
        TransactionDto transaction = aTransaction().setMinersFee("0.0001").withAmount("1.2").build();

        BindingResult result = new MapBindingResult(new HashMap<>(), "");

        transactionValidator.validateAmount(transaction, 1.1, result);

        assertEquals(0, result.getErrorCount());
    }

    @Test
    public void validateAmountWithEmptyMinersFee() {
        TransactionDto transaction = aTransaction().withCurrency("XTC").setMinersFee("").withAmount("1.2").build();
        transaction.getAsset().setDivisible(true);

        BindingResult result = new MapBindingResult(new HashMap<>(), "");

        transactionValidator.validateAmount(transaction, 1.1, result);

        assertEquals(0, result.getErrorCount());
    }

    @Test
    public void validateAsset() {
        TransactionDto transaction = aTransaction().build();

        BindingResult result = new MapBindingResult(new HashMap<>(), "");

        transactionValidator.validateAssetName(transaction, result);

        assertEquals(1, result.getErrorCount());
    }

    @Test
    public void validateWrongAmount() {
        TransactionDto transaction = aTransaction().withCurrency(CurrencyEnum.BTC.name()).setMinersFee("0.0001").withAmount("").build();

        BindingResult result = new MapBindingResult(new HashMap<>(), "");

        transactionValidator.validateAmount(transaction, 1.1, result);
        assertEquals(0, result.getErrorCount());

        transaction.setAmount("abc");
        transactionValidator.validateAmount(transaction, 1.1, result);
        assertEquals(0, result.getErrorCount());
    }

    @Test
    public void validateMinersFeeEmptyCurrency() {
        TransactionDto transaction = aTransaction().setMinersFee("0.0001").withAmount("1.2").build();

        BindingResult result = new MapBindingResult(new HashMap<>(), "");

        transactionValidator.validateMinersFee(transaction, result);

        assertEquals(0, result.getErrorCount());
    }

    @Test
    public void validateMinersFeeWrongAmount() {
        TransactionDto transaction = aTransaction().withCurrency(CurrencyEnum.BTC.name()).setMinersFee("").withAmount("1.2").build();

        BindingResult result = new MapBindingResult(new HashMap<>(), "");

        transactionValidator.validateMinersFee(transaction, result);
        assertEquals(0, result.getErrorCount());

        transaction.setMinersFee("abc");
        transactionValidator.validateMinersFee(transaction, result);
        assertEquals(0, result.getErrorCount());
    }

    @Test
    public void validateAmountRoundingFinalAmount() {
        TransactionDto transaction = aTransaction().withCurrency(CurrencyEnum.BTC.name()).setMinersFee("0.0001").withAmount("0.05004324").build();
        transaction.getAsset().setDivisible(true);

        BindingResult result = new MapBindingResult(new HashMap<>(), "");

        transactionValidator.validateAmount(transaction, 0.05014324, result);
        assertEquals(0, result.getErrorCount());
    }

    @Test
    public void validateAddress() {
        BindingResult result = new MapBindingResult(new HashMap<>(), "");
        transactionValidator.validateAddress("123456789", "address", result);

        assertEquals(1, result.getErrorCount());
        assertFieldError(result, "address", "transactionForm.invalidAddress");
    }

    @Test
    public void validateMinersFee() {
        TransactionDto transaction = aTransaction().withCurrencyMinersFee(CurrencyEnum.BTC.name(), new BigDecimal("0.0001")).setMinersFee("0.00001").build();

        BindingResult result = new MapBindingResult(new HashMap<>(), "");

        transactionValidator.validateMinersFee(transaction, result);

        assertEquals(1, result.getErrorCount());

        assertFieldError(result, "minersFee", "transactionForm.minersFee.minimum");
    }

    @Test
    public void minersFeesCanBeEqual() {
        TransactionDto transaction = aTransaction().withCurrencyMinersFee(CurrencyEnum.BTC.name(), new BigDecimal("0.0001")).setMinersFee("0.0001").build();

        BindingResult result = new MapBindingResult(new HashMap<>(), "");

        transactionValidator.validateMinersFee(transaction, result);

        assertEquals(0, result.getErrorCount());
    }

    public static void assertFieldError(BindingResult errors, String field, String expectedCode) {
        FieldError fieldError = errors.getFieldError(field);
        assertNotNull(field);
        assertThat(fieldError.getCodes(), hasItemInArray(expectedCode));
    }

}