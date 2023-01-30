package com.bonus_service.bonus_calculator;

import com.bonus_service.bonus_rule.BonusProgram;
import com.bonus_service.transaction.Transaction;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.Month;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;

@Getter
@Setter
@RequiredArgsConstructor
public class BonusStatistic {

    private final BonusProgram bonusProgram;
    @JsonIgnore
    private final List<Transaction> transactions;

    public int getTotalBonus() {
        return bonusProgram.calculateBonus(transactions);
    }

    public List<BonusMonthlyStatistic> getMonthlyStatistic() {
        Map<YearMonth, List<Transaction>> transactionsByYearMonth = transactions.stream()
                .collect(groupingBy(transaction -> YearMonth.from(transaction.getCreatedAt())));

        return transactionsByYearMonth.entrySet().stream()
                .map(it -> new BonusMonthlyStatistic(bonusProgram, it.getKey(), it.getValue()))
                .toList();
    }


}

@RequiredArgsConstructor
class BonusMonthlyStatistic {

    @JsonIgnore
    private final BonusProgram bonusProgram;
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

    public int getTotalBonus() {
        return bonusProgram.calculateBonus(transactions);
    }

}