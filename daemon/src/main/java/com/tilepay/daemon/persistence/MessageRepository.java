package com.tilepay.daemon.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tilepay.domain.entity.Issuance;
import com.tilepay.domain.entity.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query("from Issuance m where m.asset.name = :assetName")
    Issuance findIssuanceByAssetName(@Param("assetName") String assetName);
}
