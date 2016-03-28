package com.tilepay.core.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.tilepay.core.model.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
