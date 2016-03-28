package com.tilepay.daemon.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tilepay.domain.entity.Credit;

public interface CreditRepository extends JpaRepository<Credit, Long> {

}
