package com.tilepay.web.service;

import java.math.BigDecimal;
import java.math.BigInteger;

import javax.inject.Inject;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.tilepay.core.dto.TransactionDto;
import com.tilepay.core.dto.WalletDTO;
import com.tilepay.core.service.AssetQuantityConversionService;
import com.tilepay.core.service.BitcoinService;
import com.tilepay.core.service.balance.AbstractBalanceService;
import com.tilepay.core.service.balance.BalanceServiceFactory;
import com.tilepay.domain.entity.Asset;

@Component
public class TransactionValidator implements Validator {

    @Inject
    private BitcoinService bitcoinService;

    @Inject
    private ValidationService validationService;

    @Inject
    private BalanceServiceFactory balanceServiceFactory;

    @Inject
    private AssetQuantityConversionService assetQuantityConversionService;

    @Inject
    private BalanceValidator balanceValidator;

    private WalletDTO wallet;

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

        TransactionDto transaction = (TransactionDto) target;

        validateAssetName(transaction, errors);
        validateMinersFee(transaction, errors);
        validateAmount(transaction, Double.valueOf(wallet.getBalanceAvailable()), errors);
        validateAddress(transaction.getAddressFrom(), "addressFrom", errors);
        validateAddress(transaction.getAddressTo(), "addressTo", errors);
    }

    public void validateAssetName(TransactionDto transaction, Errors errors) {
        if (transaction.getAsset().getName() == null) {
            errors.rejectValue("asset", "NotNull.transactionForm.asset");
        }
    }

    public void validateMinersFee(TransactionDto transaction, Errors errors) {

        if (!isMinersFeeValidationNeeded(transaction)) {
            return;
        }

        Double formMinersFee = transaction.getMinersFeeAsBigDecimal().doubleValue();
        Double currencyMinersFee = transaction.getAsset().getMinersFee().doubleValue();

        if (formMinersFee != null && formMinersFee < currencyMinersFee) {
            errors.rejectValue("minersFee", "transactionForm.minersFee.minimum", new Object[] { String.valueOf(transaction.getAsset().getMinersFee()) }, "");
        }
    }

    public void validateAmount(TransactionDto transaction, Double btcAvailableBalance, Errors errors) {
        if (!isAmountValidationNeeded(transaction)) {
            return;
        }

        transaction.setAmount(transaction.getAmount().replace(",", "."));

        BigDecimal amount = transaction.getAmountAsBigDecimal();
        BigDecimal minersFee = transaction.getMinersFeeAsBigDecimal();

        if (!validationService.isIndivisibleAmountValid(transaction.getAsset().getDivisible(), transaction.getAmount())) {
            errors.rejectValue("amount", "transaction.amount.indivisible");
            return;
        }

        if (transaction.getAsset().isBTC()) {
            if (amount != null) {
                if (!validationService.validateMinimumBtcAmount(amount)) {
                    errors.rejectValue("amount", "transaction.minimum.BTC.amount");
                    return;
                }

                if (!balanceValidator.validateAmount(BigDecimal.valueOf(btcAvailableBalance), amount, minersFee)) {
                    errors.rejectValue("amount", "transactionForm.amount.exceeding");
                }
            }
        } else {
            //TODO: 27.01.2015 Andrei Sljusar: if indivisible -> min amount 1?
            if (!validationService.validateMinimumAssetAmount(amount.doubleValue())) {
                errors.rejectValue("amount", "transaction.minimum.asset.amount");
                return;
            }

            if (minersFee != null) {
                if (bitcoinService.isValidAddress(transaction.getAddressFrom())) {
                    Asset asset = transaction.getAsset();
                    AbstractBalanceService balanceService = balanceServiceFactory.getBalanceService(transaction.getAsset());
                    BigInteger estimatedBalance = balanceService.getEstimatedBalance(transaction.getAddressFrom(), asset);
                    balanceValidator
                            .validateBalancesOnTransactionSend(btcAvailableBalance, minersFee.doubleValue(), assetQuantityConversionService.format(asset, estimatedBalance), amount, errors);

                }
            }
        }

    }

    public void validateAddress(String address, String field, Errors errors) {
        if (!bitcoinService.isValidAddress(address)) {
            errors.rejectValue(field, "transactionForm.invalidAddress");
        }
    }

    private boolean isMinersFeeValidationNeeded(TransactionDto transaction) {
        return transaction.getAsset().getName() != null && transaction.getMinersFee() != null && validationService.isDoubleOrNumber(transaction.getMinersFee());
    }

    private boolean isAmountValidationNeeded(TransactionDto transaction) {
        return transaction.getAsset().getName() != null && validationService.isDoubleOrNumber(transaction.getAmount());
    }

}
