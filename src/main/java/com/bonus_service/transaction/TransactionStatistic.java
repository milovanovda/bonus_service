package com.bonus_service.transaction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Month;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;

@Getter
@Setter
@RequiredArgsConstructor
public class TransactionStatistic {

    @JsonIgnore
    private final List<Transaction> transactions;

    public int getTotalTransaction() {
        return transactions.size();
    }
    public BigDecimal getAverageTransaction() {
        double averageTransaction = transactions.stream()
                .mapToDouble(it -> it.getAmount().doubleValue())
                .average()
                .orElse(0);
        return BigDecimal.valueOf(averageTransaction).setScale(2, RoundingMode.HALF_UP);
    }

    public List<TransactionMonthlyStatistic> getMonthlyStatistic() {
        Map<YearMonth, List<Transaction>> transactionsByYearMonth = transactions.stream()
                .collect(groupingBy(transaction -> YearMonth.from(transaction.getCreatedAt())));

        return transactionsByYearMonth.entrySet().stream()
                .map(it -> new TransactionMonthlyStatistic(it.getKey(), it.getValue()))
                .toList();
    }


}

@RequiredArgsConstructor
class TransactionMonthlyStatistic {

    @JsonIgnore
    private final YearMonth yearMonth;

    public int getYear() {
        return yearMonth.getYear();
    }

    public Month getMonth() {
        return yearMonth.getMonth();
    }

    @JsonIgnore
    private final List<Transaction> transactions;

    public int getTotalTransaction() {
        return transactions.size();
    }
    public BigDecimal getAverageTransaction() {
        double averageTransaction = transactions.stream()
                .mapToDouble(it -> it.getAmount().doubleValue())
                .average()
                .orElse(0);
        return BigDecimal.valueOf(averageTransaction).setScale(2, RoundingMode.HALF_UP);
    }

}