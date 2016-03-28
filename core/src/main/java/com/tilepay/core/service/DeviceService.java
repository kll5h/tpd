package com.tilepay.core.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.tilepay.core.dto.DeviceRegistrationDto;
import com.tilepay.core.model.Device;
import com.tilepay.core.model.Wallet;
import com.tilepay.core.repository.DeviceRepository;
import com.tilepay.core.repository.WalletRepository;

@Service
public class DeviceService {

    @Inject
    private DeviceRepository deviceRepository;
    
    @Inject
    private WalletRepository walletRepository;
    
    public void save(DeviceRegistrationDto deviceDto){
        Device device = new Device();
        device.setAddress(deviceDto.getBTCaddress());
        device.setRegistrationToken(deviceDto.getRegistrationToken());
        device.setIPAddress(deviceDto.getIPAddress());
        device.setName(deviceDto.getName());
        device.setTxHash(deviceDto.getTxHash());
        Wallet wallet = walletRepository.findOne(deviceDto.getWalletId());
        device.setWallet(wallet);
        deviceRepository.save(device);
    }
    
    public Device getOne(Long id){
        return deviceRepository.getOne(id);
    }
    
    public List<Device> getAll(){
        return deviceRepository.findAll();
    }

    public List<Device> getDeviceList(Long walletId){
        return deviceRepository.findDeviceByWalletId(walletId);
    }
}
