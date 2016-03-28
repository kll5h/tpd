package com.tilepay.web.controller;

import static org.junit.Assert.assertEquals;

import java.util.Locale;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.MessageSource;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;

import com.tilepay.core.service.WalletAppKitHolder;

@RunWith(MockitoJUnitRunner.class)
public class LoginControllerTest extends AbstractControllerTest {

    @InjectMocks
    private LoginController loginController;

    @Mock
    private WalletAppKitHolder walletAppKitHolder;

    @Mock
    private MessageSource messageSource;

    @Test
    public void login() throws Exception {
        String message = "Blockchain is downloading";
        Locale locale = Locale.forLanguageTag("en-US");
        Model model = new ExtendedModelMap();
        Mockito.when(walletAppKitHolder.isStarting()).thenReturn(true);
        Mockito.when(messageSource.getMessage("login.blockchain.downloading", null, locale)).thenReturn(message);
        loginController.login(locale, model, null);

        assertEquals(message, model.asMap().get("downloading_msg"));

    }
}