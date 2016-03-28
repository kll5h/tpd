package com.tilepay.daemon.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tilepay.domain.entity.Asset;

public interface AssetRepository extends JpaRepository<Asset, Long> {

    //@Transactional(Transactional.TxType.NEVER)
    Asset findByName(String name);
}
