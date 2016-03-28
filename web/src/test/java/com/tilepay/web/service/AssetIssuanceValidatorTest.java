package com.tilepay.web.service;

import static com.tilepay.domain.entity.AssetBuilder.anAsset;
import static com.tilepay.web.service.TransactionValidatorTest.assertFieldError;
import static org.hamcrest.Matchers.hasItemInArray;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;
import org.springframework.validation.ObjectError;

import com.tilepay.core.dto.WalletDTO;
import com.tilepay.core.service.balance.CounterpartyBalanceService;
import com.tilepay.core.service.balance.TilecoinRestClient;
import com.tilepay.counterpartyclient.model.AssetInfo;
import com.tilepay.counterpartyclient.service.CounterpartyService;
import com.tilepay.domain.entity.Asset;
import com.tilepay.protocol.CoinConstants;

@RunWith(MockitoJUnitRunner.class)
@ActiveProfiles("unittest")
public class AssetIssuanceValidatorTest {

    @InjectMocks
    private AssetIssuanceValidator assetIssuanceValidator;

    @Mock
    private TilecoinRestClient tilecoinRestClient;

    @Mock
    private CounterpartyService counterpartyService;

    @Mock
    private CounterpartyBalanceService counterpartyBalanceService;

    @Spy
    private ValidationService validationService;

    @Spy
    private BalanceValidator balanceValidator;

    @Spy
    private WalletDTO wallet;

    @Test
    public void validateDivisibleQuantity() {

        BindingResult result = new MapBindingResult(new HashMap<>(), "");
        assetIssuanceValidator.validateQuantity("0.0", true, result);
        assertEquals(1, result.getErrorCount());
        assertFieldError(result, "quantity", "assetIssuanceForm.quantity.divisible.error");

        result = new MapBindingResult(new HashMap<>(), "");
        assetIssuanceValidator.validateQuantity("10000000000.1", true, result);
        assertEquals(1, result.getErrorCount());
        assertFieldError(result, "quantity", "assetIssuanceForm.quantity.divisible.error");

        result = new MapBindingResult(new HashMap<>(), "");
        assetIssuanceValidator.validateQuantity("0.000000001", true, result);
        assertEquals(1, result.getErrorCount());
        assertFieldError(result, "quantity", "Pattern.transactionForm.amount");

        result = new MapBindingResult(new HashMap<>(), "");
        assetIssuanceValidator.validateQuantity("10", true, result);
        assertEquals(0, result.getErrorCount());
    }

    @Test
    public void validateIndivisibleQuantity() {

        BindingResult result = new MapBindingResult(new HashMap<>(), "");
        assetIssuanceValidator.validateQuantity("10000000001", false, result);
        assertEquals(1, result.getErrorCount());
        assertFieldError(result, "quantity", "assetIssuanceForm.quantity.indivisible.error");

        result = new MapBindingResult(new HashMap<>(), "");
        assetIssuanceValidator.validateQuantity("1", false, result);
        assertEquals(1, result.getErrorCount());
        assertFieldError(result, "quantity", "assetIssuanceForm.quantity.indivisible.error");

        result = new MapBindingResult(new HashMap<>(), "");
        assetIssuanceValidator.validateQuantity("a", false, result);
        assertEquals(1, result.getErrorCount());
        assertFieldError(result, "quantity", "Pattern.transactionForm.amount");

        result = new MapBindingResult(new HashMap<>(), "");
        assetIssuanceValidator.validateQuantity("10.10", false, result);
        assertEquals(1, result.getErrorCount());
        assertFieldError(result, "quantity", "assetIssuanceForm.quantity.indivisible.error");
    }

    @Test
    public void isBigIntegerInRange() {
        boolean inRange = assetIssuanceValidator.inRange(BigInteger.ZERO, BigInteger.ONE, CoinConstants.MAX_ASSET_QUANTITY);
        assertFalse(inRange);

        inRange = assetIssuanceValidator.inRange(BigInteger.ONE, BigInteger.ONE, CoinConstants.MAX_ASSET_QUANTITY);
        assertFalse(inRange);

        inRange = assetIssuanceValidator.inRange(new BigInteger("2"), BigInteger.ONE, CoinConstants.MAX_ASSET_QUANTITY);
        assertTrue(inRange);

        inRange = assetIssuanceValidator.inRange(new BigInteger("10000000000"), BigInteger.ONE, CoinConstants.MAX_ASSET_QUANTITY);
        assertTrue(inRange);

        inRange = assetIssuanceValidator.inRange(new BigInteger("10000000001"), BigInteger.ONE, CoinConstants.MAX_ASSET_QUANTITY);
        assertFalse(inRange);
    }

    @Test
    public void isBigDecimalInRangeMin() {
        boolean inRange = assetIssuanceValidator.inRange(new BigDecimal("0.0"), BigDecimal.ZERO, new BigDecimal(CoinConstants.MAX_ASSET_QUANTITY));
        assertFalse(inRange);

        inRange = assetIssuanceValidator.inRange(new BigDecimal("1.0"), BigDecimal.ZERO, new BigDecimal(CoinConstants.MAX_ASSET_QUANTITY));
        assertTrue(inRange);
    }

    @Test
    public void validateAssetName() {
        assertAssetName("abc", 1);
        assertAssetName("ABCD", 1);
        assertAssetName("BCDE", 0);
        assertAssetName("ABCDEFGHIJKLM", 1);
        assertAssetName("bcdef", 1);
        assertAssetName("", 1);
    }

    private void assertAssetName(String assetName, int errorCount) {
        BindingResult result = new MapBindingResult(new HashMap<>(), "");
        assetIssuanceValidator.validateAssetName(assetName, result);
        assertEquals(errorCount, result.getErrorCount());
        if (errorCount > 1) {
            assertFieldError(result, "assetName", "assetIssuanceForm.assetName.error");
        }
    }

    @Test
    public void assetNameTakenError() {
        Asset asset = anAsset().setBooked(true).build();

        when(tilecoinRestClient.getAssetByName("AAAA")).thenReturn(asset);

        BindingResult result = new MapBindingResult(new HashMap<>(), "");
        assetIssuanceValidator.validateAssetName("AAAA", result);

        assertFieldError(result, "assetName", "assetIssuanceForm.assetNameTaken.error");
    }

    @Test
    public void validateTilecoinXFee() {
        BindingResult result = new MapBindingResult(new HashMap<>(), "");

        assetIssuanceValidator.validateTilecoinXFee(result);

        assertEquals(0, result.getErrorCount());
    }

    @Test
    public void validateTilecoinXFee1() {
        BindingResult result = new MapBindingResult(new HashMap<>(), "");

        wallet.setReceiveAddress("12345");
        Mockito.when(counterpartyService.getTilecoinXAssetInfo()).thenReturn(new AssetInfo());
        Mockito.when(counterpartyBalanceService.getTilecoinXEstimatedBalance("12345")).thenReturn(new BigInteger("1"));

        assetIssuanceValidator.validateTilecoinXFee(result);

        assertEquals(1, result.getErrorCount());
        ObjectError objectError = result.getAllErrors().get(0);
        assertThat(objectError.getCodes(), hasItemInArray("1"));
    }

    @Test
    public void validateBtcDustAndFee() {
        BindingResult result = new MapBindingResult(new HashMap<>(), "");
        wallet.setBalanceAvailable("0.00005143");

        assetIssuanceValidator.validateBtcDustAndFee(result);

        assertEquals(1, result.getErrorCount());
        ObjectError objectError = result.getAllErrors().get(0);
        assertThat(objectError.getCodes(), hasItemInArray("2"));

        result = new MapBindingResult(new HashMap<>(), "");
        wallet.setBalanceAvailable("0.00005144");

        assetIssuanceValidator.validateBtcDustAndFee(result);

        assertEquals(0, result.getErrorCount());

    }

}