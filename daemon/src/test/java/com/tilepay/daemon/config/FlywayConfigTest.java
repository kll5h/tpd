package com.tilepay.daemon.config;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(classes = { DataSourceConfig.class, FlywayConfig.class })
@RunWith(SpringJUnit4ClassRunner.class)
public class FlywayConfigTest {

    @Test
    public void flyway() throws Exception {

    }
}