package com.dotai.test.controller;

import com.dotai.test.entities.Transaction;
import com.dotai.test.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/transfer")
    public Transaction transferMoney(@RequestBody Transaction transaction) {
        return transactionService.transferMoney(transaction);
    }

    @GetMapping
    public List<Transaction> getTransactions(@RequestParam(required = false) String status,
                                             @RequestParam(required = false) String accountNumber,
                                             @RequestParam(required = false) LocalDate startDate,
                                             @RequestParam(required = false) LocalDate endDate) {
        return transactionService.getTransactions(status, accountNumber, startDate, endDate);
    }

    @GetMapping("/summary")
    public List<Transaction> getDailySummary(@RequestParam LocalDate date) {
        return transactionService.getDailySummary(date);
    }
}
