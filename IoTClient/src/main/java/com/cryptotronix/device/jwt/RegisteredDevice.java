/**
 * 
 */
package com.cryptotronix.device.jwt;

import java.io.IOException;
import java.security.interfaces.ECPublicKey;


/**
 * Main interface to a Registered Device.
 * @author Josh Datko <jbd@cryptotronix.com>
 *
 */
public interface RegisteredDevice {
	
	/**
	 * @return An unqiue serial number for the system.
	 * @throws IOException if there is an error.
	 */
	public String getSystemSerial() throws IOException;
	
	/**
	 * @return The serial number of the crypto device, or all zeros if software
	 */
	public String getCryptoSerial();
	
	/**
	 * @return The public key of the device
	 */
	public ECPublicKey getPublicKey();
	
	/**
	 * @return The JWT encoded token of the device.
	 */
	public String getRegistrationToken();
	
	/**
	 * Sign the data and return a serialized JWT.
	 * @param Data the data to sign, must be a JSON compatible string
	 * @return The encoded JWT
	 * @throws IOException if an eeror
	 */
	public String sign(String Data) throws IOException;
	
	
	/**
	 * Verifies an JWT token for a device
	 * @param token the serialized JWT token
	 * @return True if verified, otherwise false
	 * @throws IOException
	 */
	public boolean verify(String token) throws IOException;

}
