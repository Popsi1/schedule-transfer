package com.example.schedulefundtransfer.service;

import com.example.schedulefundtransfer.dtos.request.ScheduleTransferDto;
import com.example.schedulefundtransfer.dtos.response.ApiDataResponseDto;
import com.example.schedulefundtransfer.dtos.response.PageableResponseDto;
import com.example.schedulefundtransfer.entity.ScheduledTransfer;
import com.example.schedulefundtransfer.enums.ScheduleTransferStatus;
import com.example.schedulefundtransfer.exception.BadRequestException;
import com.example.schedulefundtransfer.exception.ResourceNotFoundException;
import com.example.schedulefundtransfer.helper.ScheduleTransferHelper;
import com.example.schedulefundtransfer.repository.ScheduledTransferRepository;
import com.example.schedulefundtransfer.util.DataResponseUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@CacheConfig(cacheNames = "userScheduledTransfers")
public class ScheduledTransferServiceImpl implements ScheduledTransferService{
    ScheduledTransferRepository scheduledTransferRepository;

    ScheduleTransferHelper scheduleTransferHelper;

    static final BigDecimal MAX_TRANSFER_LIMIT = new BigDecimal("1000000");

    public ApiDataResponseDto scheduledTransfer(ScheduleTransferDto scheduleTransferDto) {

        validatePayment(scheduleTransferDto);

        ScheduledTransfer ScheduledTransfer = scheduledTransferRepository
                .save(scheduleTransferHelper.buildUserEntity(scheduleTransferDto));

        return DataResponseUtils.successResponse("Scheduled Transfer successfully created",
                scheduleTransferHelper.buildUserResponseEntity(ScheduledTransfer));
    }

    @Cacheable(value = "userScheduledTransfers", key = "#senderAccountId + '-' + #page + '-' + #pageSize")
    public ApiDataResponseDto getScheduledTransfers(String senderAccountId, Integer page, Integer pageSize) {

        Pageable pageable = PageRequest.of(page, pageSize);
        return CompletableFuture.supplyAsync(() -> scheduledTransferRepository.findBySenderAccountId(senderAccountId, pageable))
                .thenApplyAsync(page1 -> {
                    PageableResponseDto data = PageableResponseDto.builder()
                            .totalPages(page1.getTotalPages())
                            .totalElements((int) page1.getTotalElements())
                            .pageNumber(page1.getNumber())
                            .size(page1.getSize())
                            .content(page1.getContent().stream().map(scheduleTransferHelper::buildUserResponseEntity).toList())
                            .build();
                    return DataResponseUtils.successResponse("Retrieved all user scheduled transfers", data);
                }).exceptionallyAsync(DataResponseUtils::errorResponse).join();
    }

    public ApiDataResponseDto cancelUserScheduledTransfer(String transferId) {


        UUID uuid = null;
        if (transferId.length() == 36) {
            try {
                uuid = UUID.fromString(transferId);
                System.out.println("Converted UUID: " + uuid);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid UUID string format: " + e.getMessage());
            }
        } else {
            System.out.println("Invalid UUID string length.");
        }

        assert uuid != null;
        ScheduledTransfer scheduledTransfer = scheduledTransferRepository.findById(uuid)
                .orElseThrow(() -> new ResourceNotFoundException("Scheduled transfer not found :: " + transferId));

        if (scheduledTransfer.getStatus().equals(ScheduleTransferStatus.CANCELED.name()))
            throw new RuntimeException("Scheduled transfer already cancelled");

        scheduledTransfer.setStatus(ScheduleTransferStatus.CANCELED.name());

        return DataResponseUtils.successResponse("Scheduled transfer successfully cancelled",
                scheduleTransferHelper.buildUserResponseEntity(scheduledTransferRepository.save(scheduledTransfer)));
    }

    // Validates payment-related conditions before scheduling the transfer.
    private void validatePayment(ScheduleTransferDto scheduleTransferDto) {
        // Simulated balance check (fetch from DB in real scenario)
        BigDecimal senderBalance = getSenderAccountBalance(scheduleTransferDto.getSenderAccountId());

        // Check if sender has sufficient funds
        if (senderBalance.compareTo(scheduleTransferDto.getTransferAmount()) < 0) {
            throw new BadRequestException("Insufficient funds");
        }

        // Check if transfer amount exceeds max limit
        if (scheduleTransferDto.getTransferAmount().compareTo(MAX_TRANSFER_LIMIT) > 0) {
            throw new BadRequestException("Transfer amount exceeds the allowed limit");
        }

        // Check if accounts are blocked (fetch actual status from DB)
        if (isAccountBlocked(scheduleTransferDto.getSenderAccountId()) ||
                isAccountBlocked(scheduleTransferDto.getRecipientAccountId())) {
            throw new BadRequestException("Transaction not allowed: One or both accounts are blocked");
        }

        // Check if accounts are the same
        if (compareAccounts(scheduleTransferDto.getSenderAccountId(), scheduleTransferDto.getRecipientAccountId())) {
            throw new BadRequestException("Sender account ID must not be same as recipient account ID");
        }
    }

    private BigDecimal getSenderAccountBalance(String senderAccountId) {
        return new BigDecimal("1000000");
    }

    private boolean isAccountBlocked(String accountId) {
        return false;
    }

    private boolean compareAccounts(String senderAccountId, String recipientAccountId) {
        return senderAccountId.equals(recipientAccountId);
    }

}
