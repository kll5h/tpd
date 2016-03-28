package com.tilepay.web.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.regex.Pattern;

import javax.inject.Inject;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.tilepay.core.dto.AssetIssuanceDto;
import com.tilepay.core.dto.TransactionDto;
import com.tilepay.core.dto.WalletDTO;
import com.tilepay.core.service.balance.CounterpartyBalanceService;
import com.tilepay.core.service.balance.TilecoinRestClient;
import com.tilepay.counterpartyclient.model.AssetInfo;
import com.tilepay.counterpartyclient.service.CounterpartyService;
import com.tilepay.domain.entity.Asset;
import com.tilepay.protocol.CoinConstants;

@Component
public class AssetIssuanceValidator implements Validator {

    private WalletDTO wallet;

    @Inject
    private PasswordValidator passwordValidator;

    @Inject
    private TilecoinRestClient tilecoinRestClient;

    @Inject
    private ValidationService validationService;

    @Inject
    private BalanceValidator balanceValidator;

    @Inject
    private CounterpartyBalanceService counterpartyBalanceService;

    @Inject
    private CounterpartyService counterpartyService;

    @Override
    public boolean supports(Class<?> clazz) {
        return TransactionDto.class.isAssignableFrom(clazz);
    }

    public void validate(Object target, WalletDTO wallet, Errors errors) {
        this.wallet = wallet;
        this.validate(target, errors);
    }

    @Override
    public void validate(Object target, Errors errors) {
        AssetIssuanceDto assetIssuance = (AssetIssuanceDto) target;
        assetIssuance.setQuantity(assetIssuance.getQuantity().replace(",", "."));

        validateQuantity(assetIssuance.getQuantity().trim(), assetIssuance.getDivisible(), errors);
        validateAssetName(assetIssuance.getAssetName().trim(), errors);
        validateTilecoinXFee(errors);
        validateBtcDustAndFee(errors);
        //TODO: 09.01.2015 Andrei Sljusar: first validate password, if fails -> stop other validations?
        passwordValidator.validatePassword(assetIssuance.getPassword(), errors);
    }

    public void validateTilecoinXFee(Errors errors) {
        AssetInfo tilecoinXAssetInfo = counterpartyService.getTilecoinXAssetInfo();
        if (tilecoinXAssetInfo != null) {
            BigInteger estimatedBalance = counterpartyBalanceService.getTilecoinXEstimatedBalance(wallet.getReceiveAddress());
            if (!balanceValidator.doesBalanceHaveEnoughFunds(estimatedBalance, CoinConstants.ASSET_CREATION_FEE)) {
                errors.reject("1");
            }
        }
    }

    public void validateBtcDustAndFee(Errors errors) {
        if (!balanceValidator.doesBtcBalanceHaveEnoughDustAndFee(wallet.getBtcAvailableBalanceAsBigDecimal(), new BigDecimal(CoinConstants.ASSET_CREATION_FEE_IN_BTC), 4, 2)) {
            errors.reject("2");
        }
    }

    public void validateQuantity(String quantity, boolean divisible, Errors errors) {

        if (!validationService.isDoubleOrNumber(quantity)) {
            errors.rejectValue("quantity", "Pattern.transactionForm.amount");
            return;
        }

        if (divisible) {
            BigDecimal value = new BigDecimal(quantity);
            if (!inRange(value, BigDecimal.ZERO, new BigDecimal(CoinConstants.MAX_ASSET_QUANTITY))) {
                errors.rejectValue("quantity", "assetIssuanceForm.quantity.divisible.error");
            }
        } else {
            if (validationService.isInteger(quantity)) {
                BigInteger value = new BigInteger(quantity);
                if (!inRange(value, BigInteger.ONE, CoinConstants.MAX_ASSET_QUANTITY)) {
                    errors.rejectValue("quantity", "assetIssuanceForm.quantity.indivisible.error");
                }
            } else {
                errors.rejectValue("quantity", "assetIssuanceForm.quantity.indivisible.error");
            }

        }
    }

    public boolean inRange(BigDecimal value, BigDecimal min, BigDecimal max) {
        return max.compareTo(value) >= 0 && min.compareTo(value) < 0;
    }

    public boolean inRange(BigInteger value, BigInteger min, BigInteger max) {
        return min.compareTo(value) < 0 && max.compareTo(value) >= 0;
    }

    public void validateAssetName(String name, Errors errors) {
        if (name.isEmpty()) {
            errors.rejectValue("assetName", "assetIssuanceForm.assetName.error");
            return;
        }

        Asset asset = tilecoinRestClient.getAssetByName(name);

        if (asset != null && asset.getBooked()) {
            errors.rejectValue("assetName", "assetIssuanceForm.assetNameTaken.error");
        }

        Pattern p = Pattern.compile("^[B-Z][A-Z]*$");
        Pattern p2 = Pattern.compile("^[A-Z]{4,12}$");

        if (!p.matcher(name).find() || !p2.matcher(name).find()) {
            errors.rejectValue("assetName", "assetIssuanceForm.assetName.error");
        }
    }

}
