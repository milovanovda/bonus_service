package com.bonus_service.bonus_rule;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@Log4j2
@RequiredArgsConstructor
public class BonusProgramRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    public void insertBonusProgram(BonusProgram bonusProgram) {
        log.trace("inserting bonus program:" + bonusProgram );
        jdbcTemplate.update("insert into BONUS_PROGRAM (PROGRAM_NAME, BONUS_TYPE, RULES_JSON, START_DATE, END_DATE) " +
                        "values (:programName, :bonusType, :rulesJson, :startDate, :endDate)",
                new BeanPropertySqlParameterSource(bonusProgram));
    }

    public List<BonusProgram> fetchAllBonusPrograms() {
        return jdbcTemplate.query("select * from BONUS_PROGRAM", new HashMap<>(), DataClassRowMapper.newInstance(BonusProgram.class));
    }

    public BonusProgram fetchBonusProgram(String programName) {

        return jdbcTemplate.queryForObject("select * from BONUS_PROGRAM where PROGRAM_NAME = :programName",
                Map.of("programName", programName),
                DataClassRowMapper.newInstance(BonusProgram.class));
    }

    public int count() {
        return jdbcTemplate.queryForObject("select count(*) from BONUS_PROGRAM", new HashMap<>(), Integer.class);
    }

}
