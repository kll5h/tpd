package com.tilepay.core.service.balance;

import static org.junit.Assert.assertTrue;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tilepay.core.config.CoreAppConfig;
import com.tilepay.domain.entity.Asset;
import com.tilepay.domain.entity.AssetBuilder;
import com.tilepay.domain.entity.Protocol;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = CoreAppConfig.class)
@ActiveProfiles("unittest")
public class BalanceServiceFactoryTest {

    @Inject
    private BalanceServiceFactory balanceServiceFactory;

    @Test
    public void getBalanceService() throws Exception {
        assertTrue(getBalanceService(Protocol.CNTRPRTY) instanceof CounterpartyBalanceService);
        assertTrue(getBalanceService(Protocol.TILECOIN) instanceof TilecoinBalanceService);
        assertTrue(getBalanceService(Protocol.BITCOIN) instanceof BitcoinBalanceService);
    }

    public AbstractBalanceService getBalanceService(Protocol protocol) {
        Asset asset = AssetBuilder.anAsset().setProtocol(protocol).build();
        return balanceServiceFactory.getBalanceService(asset);
    }

}