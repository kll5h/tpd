package com.tilepay.core.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tilepay.core.model.Device;

public interface DeviceRepository extends JpaRepository<Device, Long> {

    @Query("from Device model where model.wallet.id = :walletId")
    List<Device> findDeviceByWalletId(@Param("walletId") Long walletId);
}
