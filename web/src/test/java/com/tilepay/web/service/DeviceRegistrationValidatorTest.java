package com.tilepay.web.service;

import static org.hamcrest.Matchers.hasItemInArray;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.util.HashMap;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.MapBindingResult;
import org.springframework.web.client.ResourceAccessException;

import com.tilepay.core.service.DeviceRestClient;

@RunWith(MockitoJUnitRunner.class)
@ActiveProfiles("unittest")
public class DeviceRegistrationValidatorTest {

    @InjectMocks
    private DeviceRegistrationValidator deviceRegistrationValidator;

    @Mock
    private DeviceRestClient deviceRestClient;

    @Spy
    private ValidationService validationService;

    @Test
    public void validateName() {
        BindingResult result = new MapBindingResult(new HashMap<>(), "");

        deviceRegistrationValidator.validateName("", result);
        assertEquals(1, result.getErrorCount());
        
        deviceRegistrationValidator.validateName("abcdfrgktlfogtmrtoapemektlapqkemtnrktpaof", result);
        assertEquals(2, result.getErrorCount());
        
        assertFieldError(result, "name", "deviceRegistrationForm.name.error");
    }

    @Test
    public void validateIP() {

    	when(deviceRestClient.getRegistrationToken("192.168.200.200")).thenThrow(new ResourceAccessException(null));

        BindingResult result = new MapBindingResult(new HashMap<>(), "");

        deviceRegistrationValidator.validateIPAddress("192.168.200.200", result);
        assertEquals(1, result.getErrorCount());
        assertFieldError(result,"IPAddress", "deviceRegistrationForm.unreachableIP.error");
        
        result = new MapBindingResult(new HashMap<>(), "");
        deviceRegistrationValidator.validateIPAddress("999.999.999.999", result);
        assertEquals(1, result.getErrorCount());
        assertFieldError(result,"IPAddress", "deviceRegistrationForm.invalidIP.error");
    	
    }

    private void assertFieldError(BindingResult errors, String field, String expectedCode) {
        FieldError fieldError = errors.getFieldError(field);
        assertThat(fieldError.getCodes(), hasItemInArray(expectedCode));
    }
}