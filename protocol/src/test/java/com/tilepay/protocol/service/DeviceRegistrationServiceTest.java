package com.tilepay.protocol.service;

import static com.tilepay.domain.entity.DeviceRegistrationBuilder.aDeviceRegistration;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.Locale;

import javax.inject.Inject;

import org.junit.Test;

import com.tilepay.domain.entity.DeviceRegistration;

public class DeviceRegistrationServiceTest extends AbstractServiceTest {

    @Inject
    private DeviceRegistrationService deviceRegistrationService;

    @Inject
    private SHAHashConverter shaHashConverter;

    @Test
    public void compose() throws UnsupportedEncodingException {
        String registrationToken = "eyJhbGciOiJFUzI1NiJ9.eyJzdWIiOiJGMjFGQUY1"
                + "NEUwQ0MiLCJkYXRhIjoiMzUiLCJWZXJzaW9uIjoxLCJuYW1lIjoicmV5a2phdmlrIHN0YXRpb24gMSIsImlzcyI6IjAwMDAwMDAwMDA"
                + "wMDAwMDAwMCIsImRlc2NyaXB0aW9uIjoiQSB3ZWF0aGVyIHN0YXRpb24gaW4gUmV5a2phdmlrIiwiaWF0IjoxNDIyMjYwNjAxLCJwdW"
                + "JLZXkiOiJ7XCJrdHlcIjpcIkVDXCIsXCJ1c2VcIjpcInNpZ1wiLFwiY3J2XCI6XCJQLTI1NlwiLFwia2lkXCI6XCIxNDIxMTM4NjkyND"
                + "MwXCIsXCJ4XCI6XCJtY3hjMDFsOTZVU1pXMzIyaE9lZE1rZVBqQ2ZXVG0xeU9Ob29UMURHdjdBXCIsXCJ5XCI6XCJ6dmNEYkJIcERnaU"
                + "1Da2FlOVRab1hzRXE5S3dNQjFVbnJBV2NxQ2JXdVR3XCIsXCJhbGdcIjpcIkVTMjU2XCJ9In0.5GK_4Z4uYsmT726R9F6yjdCeA7ybfZ0"
                + "JMZ9c8F9KTZ9lcnm7pdiBzAWovX-yGzGtjudNfxmA1OsensOuMkObcg";

        DeviceRegistration message = aDeviceRegistration().setRegistrationToken(registrationToken).build();

        deviceRegistrationService.compose(message);

        assertTrue(protocolService.isIoTProtocol(message.getData()));

        DeviceRegistration actualMessage = deviceRegistrationService.parse(message.getData());

        BigInteger bigInteger = new BigInteger(1, shaHashConverter.convertToSHA256Hash(registrationToken));
        String expectedRegistrationToken = String.format(Locale.ENGLISH, "%040x", bigInteger);

        assertEquals(expectedRegistrationToken, actualMessage.getRegistrationToken());
    }

}