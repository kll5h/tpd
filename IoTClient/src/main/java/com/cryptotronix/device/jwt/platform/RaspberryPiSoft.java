/**
 * 
 */
package com.cryptotronix.device.jwt.platform;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.cryptotronix.device.jwt.DeviceSoft;

/**
 * @author Josh Datko <jbd@cryptotronix.com>
 *
 */
public class RaspberryPiSoft extends DeviceSoft {

	public RaspberryPiSoft() throws IOException {
		super();
	}

	@Override
	public String getSystemSerial() throws IOException {

		String cpuInfo;

		cpuInfo = new String(Files.readAllBytes(Paths.get("/proc/cpuinfo")));

		Pattern p = Pattern.compile("^Serial\\s+:\\s+(\\w+)");
		Matcher m = p.matcher(cpuInfo);
		if (m.find())
			return m.group(1);
		else
			throw new UnsupportedOperationException(
					"Error collecting CPU Serial from RPi");

	}


}
