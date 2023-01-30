package com.bonus_service.bonus_rule;

import com.bonus_service.bonus_rule.threshold.ThresholdBonusRules;
import com.bonus_service.transaction.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ThresholdBonusRulesTest {

    private ThresholdBonusRules bonusRules;

    @BeforeEach
    void setUp() {
        this.bonusRules = new ThresholdBonusRules();
        this.bonusRules.addBonusCriteria(BigDecimal.valueOf(50), BigDecimal.ONE);
        this.bonusRules.addBonusCriteria(BigDecimal.valueOf(100), BigDecimal.ONE);
    }

    @Test
    void calculateBonus() {
        assertTrue(BigDecimal.valueOf(0).compareTo(bonusRules.calculateBonus(
                new Transaction(null, null, BigDecimal.valueOf(-10.2), null))) == 0);
        assertTrue(BigDecimal.valueOf(0).compareTo(bonusRules.calculateBonus(
                new Transaction(null, null, BigDecimal.valueOf(50.5), null))) == 0);
        assertTrue(BigDecimal.valueOf(50).compareTo(bonusRules.calculateBonus(
                new Transaction(null, null, BigDecimal.valueOf(100.27), null))) == 0);
        assertTrue(BigDecimal.valueOf(90).compareTo(bonusRules.calculateBonus(
                new Transaction(null, null, BigDecimal.valueOf(120.13), null))) == 0);
    }

}