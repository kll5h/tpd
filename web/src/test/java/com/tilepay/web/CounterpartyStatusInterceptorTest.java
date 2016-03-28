package com.tilepay.web;

import static com.tilepay.counterpartyclient.model.CounterpartyStatusBuilder.aCounterpartyStatus;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;

import com.tilepay.counterpartyclient.model.CounterpartyStatus;
import com.tilepay.counterpartyclient.service.CounterpartyService;

@RunWith(MockitoJUnitRunner.class)
@ActiveProfiles("unittest")
public class CounterpartyStatusInterceptorTest {

    @InjectMocks
    CounterpartyStatusInterceptor counterpartyStatusInterceptor;

    @Mock
    CounterpartyService counterpartyService;

    @Test
    public void preHandle() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();

        Mockito.when(counterpartyService.getStatus()).thenReturn(aCounterpartyStatus().setCounterpartyd("OK").build());

        assertTrue(counterpartyStatusInterceptor.preHandle(request, new MockHttpServletResponse(), null));
        CounterpartyStatus counterpartyStatus = (CounterpartyStatus) request.getAttribute("counterpartyStatus");
        assertEquals("OK", counterpartyStatus.getCounterpartyd());
    }
}