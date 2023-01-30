package com.bonus_service.bonus_rule;

import com.bonus_service.bonus_rule.threshold.ThresholdBonusRules;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@EnableAutoConfiguration
@SpringBootTest(classes = BonusProgramRepository.class)
@Transactional
class BonusProgramRepositoryTest {

    @Autowired
    private BonusProgramRepository bonusProgramRepository;

    @Test
    void insertBonusProgram() {
        bonusProgramRepository.insertBonusProgram(createBonusProgram("TestBonusProgram 1", 10, 40));
        assertEquals(1, bonusProgramRepository.count());
    }

    @Test
    void fetchAllBonusPrograms() {
        bonusProgramRepository.insertBonusProgram(createBonusProgram("TestBonusProgram 1", 10, 40));
        bonusProgramRepository.insertBonusProgram(createBonusProgram("TestBonusProgram 2", 40, 150));

        List<BonusProgram> bonusPrograms = bonusProgramRepository.fetchAllBonusPrograms();
        assertEquals(2, bonusPrograms.size());
        assertTrue(bonusPrograms.stream().anyMatch(it -> it.getProgramName().equals("TestBonusProgram 1")));
        assertTrue(bonusPrograms.stream().anyMatch(it -> it.getProgramName().equals("TestBonusProgram 2")));
    }

    @Test
    void fetchBonusProgram() {
        bonusProgramRepository.insertBonusProgram(createBonusProgram("TestBonusProgram 1", 10, 40));
        bonusProgramRepository.insertBonusProgram(createBonusProgram("TestBonusProgram 2", 40, 150));

        assertNotNull(bonusProgramRepository.fetchBonusProgram("TestBonusProgram 1"));
    }
    @Test
    void fetchMissingBonusProgram() {
        assertThrows(EmptyResultDataAccessException.class, () -> bonusProgramRepository.fetchBonusProgram("Wrong name"));
    }

    private BonusProgram createBonusProgram(String name, int firstThreshold, int secondThreshold) {
        ThresholdBonusRules bonusRules = new ThresholdBonusRules();
        bonusRules.addBonusCriteria(BigDecimal.valueOf(firstThreshold), BigDecimal.ONE);
        bonusRules.addBonusCriteria(BigDecimal.valueOf(secondThreshold), BigDecimal.ONE);

        BonusProgram bonusProgram = new BonusProgram();
        bonusProgram.setProgramName(name);
        bonusProgram.setBonusType(bonusProgram.getBonusType());
        bonusProgram.setRulesJson(bonusRules.toJson());
        bonusProgram.setStartDate(LocalDate.of(2023, 1, 1));
        bonusProgram.setEndDate(LocalDate.of(2023, 1, 31));

        return bonusProgram;
    }
}