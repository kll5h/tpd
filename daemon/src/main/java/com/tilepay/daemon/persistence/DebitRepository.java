package com.tilepay.daemon.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tilepay.domain.entity.Debit;

public interface DebitRepository extends JpaRepository<Debit, Long> {

}
