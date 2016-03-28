package com.tilepay.web.service;

import static com.tilepay.core.dto.TransactionDTOBuilder.aTransaction;
import static com.tilepay.domain.entity.AssetBuilder.anAsset;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import java.util.Collections;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;

import com.tilepay.core.dto.TransactionDto;
import com.tilepay.core.dto.WalletDTO;
import com.tilepay.core.service.balance.AbstractBalanceService;
import com.tilepay.core.service.balance.BalanceServiceFactory;
import com.tilepay.domain.entity.Asset;

@RunWith(MockitoJUnitRunner.class)
@ActiveProfiles("unittest")
public class WalletWebServiceTest {

    @Mock
    private BalanceServiceFactory balanceServiceFactory;

    @Mock
    private TransactionValidator transactionValidator;

    @Mock
    private AbstractBalanceService abstractBalanceService;

    @InjectMocks
    private WalletWebService walletWebService;

    @Test
    public void confirmIfAssetIsNotSelected() throws Exception {
        TransactionDto transaction = new TransactionDto();

        walletWebService.confirm(null, transaction, null);

        verify(balanceServiceFactory, never()).getBalanceService(null);
        verify(transactionValidator).validate(transaction, null, null);
    }

    @Test
    public void confirmIfAssetSelected() throws Exception {
        WalletDTO wallet = new WalletDTO();
        Asset asset = anAsset().setTilecoinProtocol().build();
        TransactionDto transaction = aTransaction().setAsset(asset).build();

        BindingResult result = new MapBindingResult(Collections.emptyMap(), "");

        Mockito.when(balanceServiceFactory.getBalanceService(asset)).thenReturn(abstractBalanceService);

        walletWebService.confirm(wallet, transaction, result);

        verify(transactionValidator).validate(transaction, wallet, result);
    }
}