package com.tilepay.core.model;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.EAGER;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Wallet implements Serializable {

	private static final long serialVersionUID = 6179468280315581075L;

	@GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

//    @OneToMany(cascade = ALL, fetch = EAGER, mappedBy = "wallet")
//    private Set<Address> addresses = new HashSet<>();

    @OneToOne(cascade = ALL, fetch = EAGER, mappedBy = "wallet")
    private Address address;
    
    @OneToMany(cascade = ALL, fetch = EAGER, mappedBy = "wallet")
    private Set<Device> devices = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

//    public Set<Address> getAddresses() {
//        return addresses;
//    }
//
//    public void setAddresses(Set<Address> addresses) {
//        this.addresses = addresses;
//    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Set<Device> getDevices() {
        return devices;
    }

    public void setDevices(Set<Device> devices) {
        this.devices = devices;
    }
    
}
