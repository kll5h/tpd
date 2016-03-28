package com.tilepay.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tilepay.core.model.Address;
import com.tilepay.core.model.AssetTransaction;

public interface AssetTransactionRepository extends JpaRepository<AssetTransaction, Long> {

    @Query("from AssetTransaction model where model.address = :address and model.currency = :currency")
    List<AssetTransaction> findAllWhereAddressAndCurrency(@Param("address") Address address, @Param("currency") String currency);

    @Modifying
    @Query("delete from AssetTransaction where hash = :hash")
    void deleteOneByHash(@Param("hash") String hash);

}
