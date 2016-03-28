package com.tilepay.web.service;

import java.math.BigDecimal;
import java.util.regex.Pattern;

import javax.inject.Inject;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.client.ResourceAccessException;

import com.tilepay.core.dto.DeviceRegistrationDto;
import com.tilepay.core.dto.WalletDTO;
import com.tilepay.core.service.DeviceRestClient;
import com.tilepay.protocol.CoinConstants;

@Component
public class DeviceRegistrationValidator implements Validator {

    @Inject
    private DeviceRestClient deviceRestClient;

    @Inject
    private PasswordValidator passwordValidator;

    @Inject
    private BalanceValidator balanceValidator;

    private WalletDTO wallet;

    private static final String IPADDRESS_PATTERN = "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
            + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";

    @Override
    public boolean supports(Class<?> clazz) {
        return DeviceRegistrationDto.class.isAssignableFrom(clazz);
    }

    public void validate(Object target, WalletDTO wallet, Errors errors) {
        this.wallet = wallet;
        this.validate(target, errors);
    }

    @Override
    public void validate(Object target, Errors errors) {
        DeviceRegistrationDto deviceRegistration = (DeviceRegistrationDto) target;

        validateIPAddress(deviceRegistration.getIPAddress(), errors);
        validateName(deviceRegistration.getName(), errors);
        validateBtcDustAndFee(errors);
        passwordValidator.validatePassword(deviceRegistration.getPassword(), errors);
        // validatePassword(deviceRegistration.getPassword());
    }

    public void validateIPAddress(String ip, Errors errors) {
        Pattern p = Pattern.compile(IPADDRESS_PATTERN);
        if (!p.matcher(ip).find()) {
            errors.rejectValue("IPAddress", "deviceRegistrationForm.invalidIP.error");
        }
        try {
            deviceRestClient.getRegistrationToken(ip);
        } catch (ResourceAccessException e) {
            errors.rejectValue("IPAddress", "deviceRegistrationForm.unreachableIP.error");
        }
    }

    public void validateBtcDustAndFee(Errors errors) {
        if (!balanceValidator.doesBtcBalanceHaveEnoughDustAndFee(wallet.getBtcAvailableBalanceAsBigDecimal(), new BigDecimal(
                CoinConstants.DEVICE_REGISTRATION_FEE_IN_BTC), 0, 1)) {
            errors.reject("3");
        }
    }

    public void validateName(String name, Errors errors) {
        if (name.isEmpty()) {
            errors.rejectValue("name", "deviceRegistrationForm.name.error");
            return;
        }
        if (name.length() > 40) {
            errors.rejectValue("name", "deviceRegistrationForm.name.error");
        }
    }

}
