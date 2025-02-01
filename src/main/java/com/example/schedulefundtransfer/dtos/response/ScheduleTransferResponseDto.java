package com.example.schedulefundtransfer.dtos.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ScheduleTransferResponseDto {
    private String transferId;

    private String senderAccountId;

    private String recipientAccountId;

    private BigDecimal transferAmount;

    private String transferDate;

    private String status;

    private String createdAt;

    private String updatedAt;
}
