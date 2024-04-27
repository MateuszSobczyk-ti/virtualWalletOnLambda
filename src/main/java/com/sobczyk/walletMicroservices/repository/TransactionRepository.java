package com.sobczyk.walletMicroservices.repository;

import com.sobczyk.walletMicroservices.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
