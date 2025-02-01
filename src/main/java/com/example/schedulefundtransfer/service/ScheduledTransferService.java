package com.example.schedulefundtransfer.service;

import com.example.schedulefundtransfer.dtos.request.ScheduleTransferDto;
import com.example.schedulefundtransfer.dtos.response.ApiDataResponseDto;

import java.util.UUID;

public interface ScheduledTransferService {
    ApiDataResponseDto scheduledTransfer(ScheduleTransferDto scheduleTransferDto);
    ApiDataResponseDto getScheduledTransfers(String senderAccountId, Integer page, Integer pageSize);
    ApiDataResponseDto cancelUserScheduledTransfer(String transferId);
}
