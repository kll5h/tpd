package com.tilepay.web;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import javax.annotation.Resource;
import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.core.env.Environment;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@WebAppConfiguration
@ContextConfiguration(classes = Application.class)
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("unittest")
public class EnvironmentInterceptorTest {

    @Inject
    EnvironmentInterceptor environmentInterceptor;

    @Resource
    private Environment env;

    @Test
    public void preHandle() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        assertTrue(environmentInterceptor.preHandle(request, response, null));
        assertEquals("Running Unittest", request.getAttribute("runningEnvironment"));
        assertEquals("Version " + env.getRequiredProperty("tilepayVersion"), request.getAttribute("runningVersion"));
    }
}
