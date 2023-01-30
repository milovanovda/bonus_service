package com.bonus_service.bonus_calculator;

public interface BonusCalculatorService {
    public BonusStatistic fetchUserBonusStatistic(String bonusProgramName, String userId);
}
