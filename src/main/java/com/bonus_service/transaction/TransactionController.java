package com.bonus_service.transaction;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("transaction")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionRepository transactionRepository;

    @GetMapping("list-users")
    public List<String> fetchAllUsers() {
        return transactionRepository.fetchAllUsers();
    }

    @GetMapping("list-user-transactions")
    public List<Transaction> fetchUserTransactions(
            @RequestParam("userId") String userId,
            @RequestParam("startDate") LocalDate startDate,
            @RequestParam("endDate") LocalDate endDate) {

        return transactionRepository.fetchUserTransactions(userId, startDate, endDate);
    }

    @GetMapping("list-user-transaction-statistic")
    public TransactionStatistic fetchUserTransactionStatistic(
            @RequestParam("userId") String userId,
            @RequestParam("startDate") LocalDate startDate,
            @RequestParam("endDate") LocalDate endDate) {

        return new TransactionStatistic(transactionRepository.fetchUserTransactions(userId, startDate, endDate));
    }

}
