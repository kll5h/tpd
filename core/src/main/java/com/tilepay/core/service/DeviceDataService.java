package com.tilepay.core.service;

import java.text.ParseException;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.nimbusds.jwt.SignedJWT;
import com.tilepay.core.model.DeviceData;
import com.tilepay.core.repository.DeviceDataRepository;

@Service
public class DeviceDataService {

	@Inject
    private DeviceDataRepository deviceDataRepository;
	
	@Inject 
	private DeviceService deviceService;
	
	public List<DeviceData> getAll(){
		return deviceDataRepository.findAll();
	}
	
	public void save(String dataToken, Long deviceId){
			SignedJWT jws;
			try {
				jws = SignedJWT.parse(dataToken);
				String data = jws.getJWTClaimsSet().getStringClaim("data");
				
				if(data != null){
					DeviceData deviceData = new DeviceData();
					deviceData.setData(data);
					deviceData.setTime(new Date());
					deviceData.setDeviceId(deviceService.getOne(deviceId));
					deviceDataRepository.save(deviceData);
				}
				
			} catch (ParseException e) {
			}

	}
	
	public String getDataForDevice(Long deviceId){
		List<DeviceData> dataList = getAll();
		Collections.sort(dataList, new Comparator<DeviceData>() {
		    public int compare(DeviceData m1, DeviceData m2) {
		        return m1.getTime().compareTo(m2.getTime());
		    }
		});
		for(DeviceData data: dataList){
			if(data.getDeviceId().getId().equals(deviceId)){
				return data.getData();
			}
		}
		return "";
	}

}
