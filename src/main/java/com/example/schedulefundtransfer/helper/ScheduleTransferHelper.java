package com.example.schedulefundtransfer.helper;

import com.example.schedulefundtransfer.dtos.request.ScheduleTransferDto;
import com.example.schedulefundtransfer.dtos.response.ScheduleTransferResponseDto;
import com.example.schedulefundtransfer.entity.ScheduledTransfer;
import com.example.schedulefundtransfer.enums.ScheduleTransferStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class ScheduleTransferHelper {

    public ScheduledTransfer buildUserEntity(ScheduleTransferDto scheduleTransferDto){
        System.out.println(55555);
        return ScheduledTransfer.builder()
                .recipientAccountId(scheduleTransferDto.getRecipientAccountId())
                .senderAccountId(scheduleTransferDto.getSenderAccountId())
                .transferAmount(scheduleTransferDto.getTransferAmount())
                .transferDate(LocalDateTime.parse(scheduleTransferDto.getTransferDate()))
                .status(ScheduleTransferStatus.PENDING.name())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    public ScheduleTransferResponseDto buildUserResponseEntity(ScheduledTransfer scheduledTransfer){
        return ScheduleTransferResponseDto.builder()
                .transferId(String.valueOf(scheduledTransfer.getTransferId()))
                .recipientAccountId(scheduledTransfer.getRecipientAccountId())
                .senderAccountId(scheduledTransfer.getSenderAccountId())
                .transferDate(String.valueOf(scheduledTransfer.getTransferDate()))
                .transferAmount(scheduledTransfer.getTransferAmount())
                .status(scheduledTransfer.getStatus())
                .createdAt(String.valueOf(scheduledTransfer.getCreatedAt()))
                .updatedAt(String.valueOf(scheduledTransfer.getUpdatedAt()))
                .build();
    }
}
