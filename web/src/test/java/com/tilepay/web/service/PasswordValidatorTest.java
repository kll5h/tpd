package com.tilepay.web.service;

import static org.hamcrest.Matchers.hasItemInArray;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.util.HashMap;

import org.bitcoinj.core.Wallet;
import org.bitcoinj.kits.WalletAppKit;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.MapBindingResult;

import com.tilepay.core.service.WalletAppKitHolder;

@RunWith(MockitoJUnitRunner.class)
public class PasswordValidatorTest {

    @Mock
    private SessionService sessionService;

    @InjectMocks
    private PasswordValidator passwordValidator;

    @Mock
    private WalletAppKitHolder walletAppKitHolder;

    @Mock
    private WalletAppKit kit;

    @Mock
    private Wallet wallet;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void emptyPassword() throws Exception {
        BindingResult errors = new MapBindingResult(new HashMap<>(), "");

        passwordValidator.validatePassword("", errors);

        assertEquals(1, errors.getErrorCount());

        FieldError password = errors.getFieldError("password");
        assertThat(password.getCodes(), hasItemInArray("empty.password"));
    }

    @Test
    public void incorrectPassword() throws Exception {
        BindingResult errors = new MapBindingResult(new HashMap<>(), "");

        when(sessionService.getAccountId()).thenReturn(1L);
        when(walletAppKitHolder.getWalletAppKit(1L)).thenReturn(kit);
        when(kit.wallet()).thenReturn(wallet);
        when(wallet.isEncrypted()).thenReturn(true);

        passwordValidator.validatePassword("xxx", errors);

        assertEquals(1, errors.getErrorCount());

        FieldError password = errors.getFieldError("password");
        assertThat(password.getCodes(), hasItemInArray("incorrect.password"));
    }

    @Test
    public void correctPassword() {
        BindingResult errors = new MapBindingResult(new HashMap<>(), "");

        when(sessionService.getAccountId()).thenReturn(1L);
        when(walletAppKitHolder.getWalletAppKit(1L)).thenReturn(kit);
        when(kit.wallet()).thenReturn(wallet);
        when(wallet.isEncrypted()).thenReturn(true);
        when(wallet.checkPassword("123456")).thenReturn(true);

        passwordValidator.isPasswordCorrect(1L, "123456");

        assertEquals(0, errors.getErrorCount());
    }

    @Test
    public void dontHaveToCheckPasswordForUnencryptedWalletException() {

        when(walletAppKitHolder.getWalletAppKit(1L)).thenReturn(kit);
        when(kit.wallet()).thenReturn(wallet);
        when(wallet.isEncrypted()).thenReturn(false);

        thrown.expect(RuntimeException.class);
        thrown.expectMessage("You don't have to check password for unencrypted wallet. Wallet id: 1");

        passwordValidator.isPasswordCorrect(1L, "123456");

    }
}