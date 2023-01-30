package com.bonus_service.bonus_rule;

import com.bonus_service.bonus_rule.threshold.ThresholdBonusRules;
import com.bonus_service.transaction.Transaction;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@ToString
public class BonusProgram {

    @JsonIgnore
    private Long id;
    private String programName;
    @JsonIgnore
    private String bonusType;
    @JsonIgnore
    private String rulesJson;
    private LocalDate startDate;
    private LocalDate endDate;

    @JsonIgnore
    public BonusRules getBonusRules() {
        switch (BonusType.valueOf(bonusType)) {
            case THRESHOLD -> {
                ThresholdBonusRules bonusRules = new ThresholdBonusRules();
                bonusRules.fromJson(rulesJson);
                return bonusRules;
            }
            default -> throw new UnsupportedOperationException();
        }
    }

    public int calculateBonus(List<Transaction> transactions) {
        BonusRules bonusRules = getBonusRules();

        return transactions.stream()
                .filter(transaction -> !transaction.getCreatedAt().toLocalDate().isBefore(startDate)
                        && !transaction.getCreatedAt().toLocalDate().isAfter(endDate))
                .map(bonusRules::calculateBonus)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .intValue();
    }
}
