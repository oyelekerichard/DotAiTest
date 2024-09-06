package com.dotai.test.service;

import com.dotai.test.entities.Transaction;
import com.dotai.test.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    public Transaction transferMoney(Transaction transaction) {
        // Validate transaction
        if (transaction.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero.");
        }

        // Calculate fees
        BigDecimal transactionFee = calculateTransactionFee(transaction.getAmount());
        BigDecimal billedAmount = transaction.getAmount().add(transactionFee);

        // Set transaction details
        transaction.setTransactionFee(transactionFee);
        transaction.setBilledAmount(billedAmount);
        transaction.setStatus("SUCCESSFUL");
        transaction.setDateCreated(LocalDateTime.now());

        // Save to database
        return transactionRepository.save(transaction);
    }

    private BigDecimal calculateTransactionFee(BigDecimal amount) {
        BigDecimal fee = amount.multiply(BigDecimal.valueOf(0.005));
        return fee.compareTo(BigDecimal.valueOf(100)) > 0 ? BigDecimal.valueOf(100) : fee;
    }

    public List<Transaction> getTransactions(String status, String accountNumber, LocalDate startDate, LocalDate endDate) {
        return transactionRepository.findByStatusAndAccountNumberAndDateCreatedBetween(status, accountNumber, startDate, endDate);
    }

    public List<Transaction> getDailySummary(LocalDate date) {
        return transactionRepository.findByDateCreated(date);
    }
}
