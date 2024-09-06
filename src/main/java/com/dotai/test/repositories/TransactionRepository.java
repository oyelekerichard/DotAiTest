package com.dotai.test.repositories;

import com.dotai.test.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByStatusAndAccountNumberAndDateCreatedBetween(String status, String accountNumber, LocalDate startDate, LocalDate endDate);
    List<Transaction> findByDateCreated(LocalDate dateCreated);
}