package com.bonus_service.bonus_rule;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("bonus-program")
@RequiredArgsConstructor
public class BonusProgramController {

    private final BonusProgramRepository bonusProgramRepository;

    @GetMapping("list")
    public List<String> getAllBonusPrograms() {
        return bonusProgramRepository.fetchAllBonusPrograms().stream()
                .map(BonusProgram::getProgramName)
                .toList();
    }

}
