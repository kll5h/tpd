package com.tilepay.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tilepay.core.model.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {

    @Query("from Address model where model.address = :address")
    Address findOneByAddress(@Param("address") String address);
}
