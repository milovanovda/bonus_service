package com.bonus_service.bonus_rule;

import com.bonus_service.transaction.Transaction;

import java.math.BigDecimal;

public interface BonusRules {

    BonusType getBonusType();

    String toJson();

    void fromJson(String text);

    BigDecimal calculateBonus(Transaction transaction);

}
