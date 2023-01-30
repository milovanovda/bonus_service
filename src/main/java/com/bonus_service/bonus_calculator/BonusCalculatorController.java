package com.bonus_service.bonus_calculator;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("bonus-calculator")
@RequiredArgsConstructor
public class BonusCalculatorController {

    private final BonusCalculatorService bonusCalculatorService;


    @GetMapping("list-user-bonus-statistic")
    public BonusStatistic fetchUserBonusStatistic(
            @RequestParam("bonusProgramName") String bonusProgramName,
            @RequestParam("userId") String userId) {

        return bonusCalculatorService.fetchUserBonusStatistic(bonusProgramName, userId);
    }

}
