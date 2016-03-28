package com.tilepay.protocol.service;

import javax.inject.Inject;

import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tilepay.protocol.config.ProtocolConfig;

@ContextConfiguration(classes = ProtocolConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("unittest")
public abstract class AbstractServiceTest {

    @Inject
    protected ProtocolService protocolService;

}