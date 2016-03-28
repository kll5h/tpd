package com.tilepay.core.config;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(classes = CoreAppConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles({ "unittest" })
public class CoreAppConfigTest {

    @Test
    public void test() throws Exception {
    }

}