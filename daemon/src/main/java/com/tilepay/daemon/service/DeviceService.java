package com.tilepay.daemon.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.tilepay.daemon.persistence.DeviceRepository;
import com.tilepay.domain.entity.Device;

@Service
public class DeviceService {

    @Inject
    protected DeviceRepository deviceRepository;

    public void save(Device device){
    	deviceRepository.save(device);
    }
    
    public Device getOne(Long id){
    	return deviceRepository.getOne(id);
    }
    
    public void updateDeviceIP(String ip, Long id){
    	Device device = getOne(id);
    	device.setIPAddress(ip);
    	save(device);
    }
}
