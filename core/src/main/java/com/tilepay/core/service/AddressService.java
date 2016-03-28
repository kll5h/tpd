package com.tilepay.core.service;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.tilepay.core.model.Address;
import com.tilepay.core.repository.AddressRepository;

@Service
public class AddressService {

    @Inject
    private AddressRepository addressRepository;

    public Address save(Address address) {
        for (Address existingAddress : findAll()) {
            if (Objects.equals(existingAddress.getAddress(), address.getAddress()))
                return existingAddress;
        }
        this.addressRepository.save(address);
        return address;
    }

    public Address findOne(Long id) {
        return addressRepository.getOne(id);
    }

    public Address findOneByAddress(String address) {
        return addressRepository.findOneByAddress(address);
    }

    public List<Address> findAll() {
        return addressRepository.findAll();
    }

}
