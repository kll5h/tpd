package com.tilepay.daemon.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tilepay.domain.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Transactional(propagation = Propagation.NEVER)
    @Query("from Transaction t where t.message.id in (select i.id from Issuance i where i.status = 0)")
    List<Transaction> findTransactionsWithoutFeeCharged();

    // TODO: 21.01.2015 Andrei Sljusar: fee transactions
    @Transactional(propagation = Propagation.NEVER)
    List<Transaction> findBySourceAndDestination(String source, String destination);

    @Transactional(propagation = Propagation.NEVER)
    @Query("from Transaction t where t.message.id in (select i.id from Issuance i where i.status = 0) and t.source = :source")
    List<Transaction> findIssuanceTransactionsWithoutFeeCharged(@Param("source") String source);
}
