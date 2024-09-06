package com.dotai.test.scheduler;

import com.dotai.test.entities.Transaction;
import com.dotai.test.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Component
public class DotAiScheduler {

    @Autowired
    private TransactionRepository transactionRepository;

    @Scheduled(cron = "0 0 2 * * ?") // Runs daily at 2 AM
    public void updateCommissionWorthy() {
        List<Transaction> transactions = transactionRepository.findAll();
        for (Transaction transaction : transactions) {
            if ("SUCCESSFUL".equals(transaction.getStatus()) && !transaction.isCommissionWorthy()) {
                transaction.setCommissionWorthy(true);
                transaction.setCommission(transaction.getTransactionFee().multiply(BigDecimal.valueOf(0.2)));
                transactionRepository.save(transaction);
            }
        }
    }

    @Scheduled(cron = "0 0 3 * * ?") // Runs daily at 3 AM
    public void generateDailySummary() {
        // Get the current date
        LocalDate today = LocalDate.now();
        List<Transaction> transactions = transactionRepository.findByDateCreated(today);

        // Generate summary (example: print to console)
        System.out.println("Summary for " + today + ":");
        for (Transaction transaction : transactions) {
            System.out.println(transaction);
        }
    }

}