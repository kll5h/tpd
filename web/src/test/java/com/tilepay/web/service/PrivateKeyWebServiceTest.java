package com.tilepay.web.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.tilepay.core.dto.ResponseDTO;
import com.tilepay.core.dto.WalletDTO;
import com.tilepay.core.dto.WalletDTOBuilder;
import com.tilepay.core.model.Account;
import com.tilepay.core.model.Wallet;
import com.tilepay.core.service.WalletService;

@RunWith(MockitoJUnitRunner.class)
public class PrivateKeyWebServiceTest {

    @InjectMocks
    private PrivateKeyWebService privateKeyWebService;

    @Mock
    private SessionService sessionService;

    @Mock
    private WalletService walletService;

    @Mock
    private PasswordValidator passwordValidator;

    @Test
    public void confirmPassphrase() throws Exception {
        String password = "password";
        String privateKey = "123";

        Account mockAccount = new Account();
        mockAccount.setWallet(new Wallet());
        mockAccount.setId(1L);

        WalletDTO wallet = WalletDTOBuilder.aWallet().withPrivateKey(privateKey).build();

        when(sessionService.getAccount()).thenReturn(mockAccount);
        when(walletService.loadWallet(mockAccount.getWallet().getId(), password)).thenReturn(wallet);
        when(passwordValidator.isPasswordCorrect(mockAccount.getWallet().getId(), password)).thenReturn(true);

        ResponseDTO privateKeyDTO = privateKeyWebService.confirmPassword(password);
        assertEquals(wallet.getPrivateKey(), privateKeyDTO.getMessage());
    }
}