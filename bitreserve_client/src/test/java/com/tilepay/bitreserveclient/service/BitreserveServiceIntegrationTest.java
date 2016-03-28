package com.tilepay.bitreserveclient.service;

import javax.inject.Inject;

import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tilepay.bitreserveclient.config.BitreserveClientConfig;
import com.tilepay.bitreserveclient.service.authorization.AuthorizationService;
import com.tilepay.bitreserveclient.service.card.CardService;
import com.tilepay.bitreserveclient.service.contact.ContactService;
import com.tilepay.bitreserveclient.service.ticker.TickerService;
import com.tilepay.bitreserveclient.service.transaction.TransactionService;
import com.tilepay.bitreserveclient.service.user.UserService;

@ContextConfiguration(classes = BitreserveClientConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("testnet")
public class BitreserveServiceIntegrationTest {

    @Inject
    private AuthorizationService authorizationService;
    
    @Inject
    private CardService cardService;
    
    @Inject
    private ContactService contactService;
    
    @Inject
    private TickerService tickerService;
    
    @Inject
    private TransactionService transactionService;
    
    @Inject
    private UserService userService;
    
    @Rule
    public ExpectedException thrown = ExpectedException.none();

   

}