package com.tilepay.core.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.tilepay.core.model.DeviceData;

public interface DeviceDataRepository extends JpaRepository<DeviceData, Long> {

}
