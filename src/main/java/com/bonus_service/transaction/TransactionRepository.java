package com.bonus_service.transaction;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class TransactionRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public List<String> fetchAllUsers() {
        return jdbcTemplate.queryForList("select distinct USER_ID from TRANSACTIONS", new HashMap<>(), String.class);
    }

    public List<Transaction> fetchUserTransactions(String userId, LocalDate startDate, LocalDate endDate) {
        return jdbcTemplate.query("select * from TRANSACTIONS " +
                " where USER_ID = :userId " +
                "   and CREATED_AT >= :startDate" +
                "   and CREATED_AT < :endDate " +
                " order by CREATED_AT",
                Map.of("userId", userId,
                        "startDate", startDate,
                        "endDate", endDate.plusDays(1)),
                DataClassRowMapper.newInstance(Transaction.class));
    }
}
