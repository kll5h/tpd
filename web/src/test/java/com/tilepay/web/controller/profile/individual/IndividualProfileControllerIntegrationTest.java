package com.tilepay.web.controller.profile.individual;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.tilepay.core.model.Account;
import com.tilepay.core.model.AccountBuilder;
import com.tilepay.core.model.AccountType;
import com.tilepay.core.service.AccountService;
import com.tilepay.core.service.LoginService;
import com.tilepay.web.Application;

@ContextConfiguration(classes = { Application.class })
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ActiveProfiles({ "unittest", "local-testnet" })
public class IndividualProfileControllerIntegrationTest {

    @Inject
    private WebApplicationContext wac;

    protected MockMvc mockMvc;

    @Inject
    private AccountService accountService;

    @Inject
    private LoginService loginService;

    @Before
    public void before() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void updatePrivateDetails() throws Exception {
        Account account = AccountBuilder.anAccount().setAccountType(AccountType.INDIVIDUAL).setPassphrase("123").build();
        accountService.createAccount(account);

        MockHttpServletRequestBuilder request = post("/profile/individual/private").with(user(loginService.loadUserByUsername("123")));
        request.param("country", "13");

        //TODO: 09.10.2014 Andrei Sljusar: how to test PrivateDetails privateDetails?
        ResultActions response = mockMvc.perform(request);

        response.andExpect(flash().attribute("activeTab", "PRIVATE"));
        response.andExpect(flash().attribute("privateDetailsSaveSuccess", true));
        response.andExpect(redirectedUrl("/profile/individual/"));
        response.andExpect(status().isFound());
    }

}
