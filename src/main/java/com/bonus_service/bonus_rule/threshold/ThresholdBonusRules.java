package com.bonus_service.bonus_rule.threshold;

import com.bonus_service.bonus_rule.BonusRules;
import com.bonus_service.bonus_rule.BonusType;
import com.bonus_service.transaction.Transaction;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 * Current version of bonus rules allows to generate bonuses based on threshold lists.
 * Each rule is independent and can be applied to the same transaction simultaneously
 * Example:
 * - 50 -> +1 for each dollar
 * - 100 -> +1 for each dollar (+2 in total)
 */
public class ThresholdBonusRules implements BonusRules {

    private static final BonusType BONUS_TYPE = BonusType.THRESHOLD;

    private List<Rule> rules = new ArrayList<>();

    public void addBonusCriteria(BigDecimal threshold, BigDecimal bonus) {
        rules.add(new Rule(threshold, bonus));
    }

    @Override
    public BigDecimal calculateBonus(Transaction transaction) {
        return rules.stream()
                .filter(rule -> transaction.getAmount().compareTo(rule.threshold) > 0)
                .map(rule -> transaction.getAmount().subtract(rule.threshold)
                        .setScale(0, RoundingMode.FLOOR)
                        .multiply(rule.bonus))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public BonusType getBonusType() {
        return BONUS_TYPE;
    }

    @SneakyThrows
    @Override
    public String toJson() {
        return new ObjectMapper().writeValueAsString(rules);
    }

    @SneakyThrows
    @Override
    public void fromJson(String text) {
        this.rules = new ObjectMapper().readValue(text, new TypeReference<>() {});
    }


    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    static class Rule {
        private BigDecimal threshold;
        private BigDecimal bonus;
    }

}
