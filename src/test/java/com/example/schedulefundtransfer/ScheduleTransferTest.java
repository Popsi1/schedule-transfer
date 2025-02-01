package com.example.schedulefundtransfer;

import com.example.schedulefundtransfer.dtos.request.ScheduleTransferDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ScheduleTransferTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void scheduleTransfer() throws Exception {

        ScheduleTransferDto scheduleTransferDto = new ScheduleTransferDto();
        scheduleTransferDto.setTransferAmount(BigDecimal.valueOf(50));
        scheduleTransferDto.setSenderAccountId("1111111111");
        scheduleTransferDto.setRecipientAccountId("2222222222");
        scheduleTransferDto.setTransferDate("2026-02-01T10:40:42.006102");

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/api/transfer/schedules")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(scheduleTransferDto)));

        result.andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(print());
    }

    @Test
    public void getscheduleTransfers() throws Exception {

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders
                .get("/api/transfer/schedules/{senderAccountId}", "senderAccountId", "1111111111"));

        result.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());
    }

    @Test
    public void cancelscheduleTransfers() throws Exception {

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders
                .patch("/api/transfer/schedules/cancel/{transferId}", "transferId", "1111111111"));

        result.andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andDo(print());
    }

}
