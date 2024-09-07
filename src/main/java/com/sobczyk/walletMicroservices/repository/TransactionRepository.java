package com.sobczyk.walletMicroservices.repository;

import com.sobczyk.walletMicroservices.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query(value = "select t from Transaction t where t.investorId =:investorId order by t.asset.ticker, t.transaction_date")
    List<Transaction> findTransactions(Long investorId);
}
