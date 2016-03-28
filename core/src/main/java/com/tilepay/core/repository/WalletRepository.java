package com.tilepay.core.repository;


import com.tilepay.core.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface WalletRepository extends JpaRepository<Wallet, Long> {

    @Query("select max(id) from Wallet")
    Long selectWalletMaxId();
}
