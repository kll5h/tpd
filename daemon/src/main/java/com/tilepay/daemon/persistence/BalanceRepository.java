package com.tilepay.daemon.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tilepay.domain.entity.Balance;

public interface BalanceRepository extends JpaRepository<Balance, Long> {

    List<Balance> findByAddressOrderByAssetNameAsc(String address);

    Balance findByAddressAndAssetName(String address, String assetName);
}
