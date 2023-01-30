package com.bonus_service.bonus_calculator;

import com.bonus_service.bonus_rule.BonusProgram;
import com.bonus_service.bonus_rule.BonusProgramRepository;
import com.bonus_service.transaction.Transaction;
import com.bonus_service.transaction.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BonusCalculatorServiceImpl implements BonusCalculatorService {

    private final BonusProgramRepository bonusProgramRepository;
    private final TransactionRepository transactionRepository;


    public BonusStatistic fetchUserBonusStatistic(String bonusProgramName, String userId) {
        BonusProgram bonusProgram = bonusProgramRepository.fetchBonusProgram(bonusProgramName);
        List<Transaction> transactions = transactionRepository.fetchUserTransactions(userId, bonusProgram.getStartDate(), bonusProgram.getEndDate());

        return new BonusStatistic(bonusProgram, transactions);
    }
}
