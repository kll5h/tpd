package com.tilepay.daemon.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tilepay.domain.entity.Issuance;

public interface _IssuanceRepository extends JpaRepository<Issuance, Long> {
}
