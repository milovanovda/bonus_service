package com.bonus_service.bonus_calculator;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BonusCalculatorControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @Test
    void contextLoads() {
        assertNotNull(mockMvc);
    }

    @Test
    void fetchUserBonusStatistic() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                .get("/bonus-calculator/list-user-bonus-statistic?bonusProgramName=TestProgramOne&userId=John");

        mockMvc.perform(request)
                .andExpect(status().is2xxSuccessful());

        request = MockMvcRequestBuilders
                .get("/bonus-calculator/list-user-bonus-statistic?bonusProgramName=SuchProgramDoesNotExist&userId=John");
        //not existing program name should return 404 status
        mockMvc.perform(request)
                .andExpect(status().is4xxClientError());
    }
}