package com.bonus_service.initial_population;

import com.bonus_service.bonus_rule.BonusProgram;
import com.bonus_service.bonus_rule.BonusProgramRepository;
import com.bonus_service.bonus_rule.threshold.ThresholdBonusRules;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.time.LocalDate;

@Configuration
@RequiredArgsConstructor
public class BonusRulesInitializer {

    private final BonusProgramRepository bonusProgramRepository;

    @PostConstruct
    public void init() {
        addBonusProgramOne();
        addBonusProgramTwo();
    }

    private void addBonusProgramOne() {
        BonusProgram bonusProgram = new BonusProgram();

        ThresholdBonusRules thresholdBonusRules = new ThresholdBonusRules();
        thresholdBonusRules.addBonusCriteria(BigDecimal.valueOf(50), BigDecimal.ONE);
        thresholdBonusRules.addBonusCriteria(BigDecimal.valueOf(100), BigDecimal.ONE);

        bonusProgram.setProgramName("TestProgramOne");
        bonusProgram.setBonusType(thresholdBonusRules.getBonusType().name());
        bonusProgram.setRulesJson(thresholdBonusRules.toJson());
        bonusProgram.setStartDate(LocalDate.of(2022, 7, 1));
        bonusProgram.setEndDate(LocalDate.of(2022, 9, 15));

        bonusProgramRepository.insertBonusProgram(bonusProgram);
    }

    private void addBonusProgramTwo() {
        BonusProgram bonusProgram = new BonusProgram();

        ThresholdBonusRules thresholdBonusRules = new ThresholdBonusRules();
        thresholdBonusRules.addBonusCriteria(BigDecimal.valueOf(25), BigDecimal.ONE);
        thresholdBonusRules.addBonusCriteria(BigDecimal.valueOf(50), BigDecimal.ONE);

        bonusProgram.setProgramName("TestProgramTwo");
        bonusProgram.setBonusType(thresholdBonusRules.getBonusType().name());
        bonusProgram.setRulesJson(thresholdBonusRules.toJson());
        bonusProgram.setStartDate(LocalDate.of(2022, 6, 1));
        bonusProgram.setEndDate(LocalDate.of(2022, 7, 15));

        bonusProgramRepository.insertBonusProgram(bonusProgram);
    }


}
