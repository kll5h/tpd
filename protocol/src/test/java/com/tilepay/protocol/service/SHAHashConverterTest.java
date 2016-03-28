package com.tilepay.protocol.service;

import static org.junit.Assert.assertArrayEquals;

import javax.inject.Inject;

import org.junit.Test;

public class SHAHashConverterTest extends AbstractServiceTest {

    @Inject
    private SHAHashConverter shaHashConverter;

    @Test
    public void convertToSHA256Hash() {
    	
    	String jwt = "eyJhbGciOiJFUzI1NiJ9.eyJzdWIiOiJGMjFGQUY1NEUwQ0MiLC"
    			+ "JkYXRhIjoiMzUiLCJWZXJzaW9uIjoxLCJuYW1lIjoicmV5a2phdmlrI"
    			+ "HN0YXRpb24gMSIsImlzcyI6IjAwMDAwMDAwMDAwMDAwMDAwMCIsImRl"
    			+ "c2NyaXB0aW9uIjoiQSB3ZWF0aGVyIHN0YXRpb24gaW4gUmV5a2phdml"
    			+ "rIiwiaWF0IjoxNDIyMjYwNjAxLCJwdWJLZXkiOiJ7XCJrdHlcIjpcIkV"
    			+ "DXCIsXCJ1c2VcIjpcInNpZ1wiLFwiY3J2XCI6XCJQLTI1NlwiLFwia2"
    			+ "lkXCI6XCIxNDIxMTM4NjkyNDMwXCIsXCJ4XCI6XCJtY3hjMDFsOTZVU1"
    			+ "pXMzIyaE9lZE1rZVBqQ2ZXVG0xeU9Ob29UMURHdjdBXCIsXCJ5XCI6XC"
    			+ "J6dmNEYkJIcERnaU1Da2FlOVRab1hzRXE5S3dNQjFVbnJBV2NxQ2JXdV"
    			+ "R3XCIsXCJhbGdcIjpcIkVTMjU2XCJ9In0.5GK_4Z4uYsmT726R9F6yjdC"
    			+ "eA7ybfZ0JMZ9c8F9KTZ9lcnm7pdiBzAWovX-yGzGtjudNfxmA1OsensOuMkObcg";
    	
    	byte[] expectedBytes={-111, 19, 22, 59, -47, -58, -57, -106, 3, -55, 121, 106,
    			-35, 92, -100, -85, -62, 24, -123, 22, 71, -67, 88, 6, 22, 36, 25, 120,
    			-39, 63, 95, -39};
    	
    	byte[] jwtBytes = shaHashConverter.convertToSHA256Hash(jwt);
    	
    	assertArrayEquals(jwtBytes,expectedBytes);
    }
    
}