package com.tilepay.daemon.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tilepay.domain.entity.Device;

public interface DeviceRepository extends JpaRepository<Device, Long> {

}
