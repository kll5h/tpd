package com.cryptotronix.device.sensor;

public class UsedMemorySensor implements Sensor {

	@Override
	public String getData() {
		Runtime runtime = Runtime.getRuntime();
		
		int mb = 1024 * 1024;
		
		return String.valueOf((runtime.totalMemory() - runtime.freeMemory()) / mb);
	}

}
