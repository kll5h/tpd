package com.tilepay.core.service.balance;

import javax.inject.Inject;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tilepay.core.config.CoreAppConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = CoreAppConfig.class)
@ActiveProfiles("unittest")
public class TilecoinRestClientTest {

    @Inject
    private TilecoinRestClient tilecoinRestClient;

    @Ignore
    @Test
    public void getBalances() throws Exception {
        tilecoinRestClient.getBalances("1111");
    }
}