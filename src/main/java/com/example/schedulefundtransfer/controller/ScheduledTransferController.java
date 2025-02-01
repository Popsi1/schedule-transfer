package com.example.schedulefundtransfer.controller;

import com.example.schedulefundtransfer.dtos.request.ScheduleTransferDto;
import com.example.schedulefundtransfer.dtos.response.ApiDataResponseDto;
import com.example.schedulefundtransfer.service.ScheduledTransferService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/transfer/schedules")
@Tag(name = "Schedule Transfer Route")
public class ScheduledTransferController {
    private final ScheduledTransferService scheduledTransferService;

    @PostMapping
    @Operation(
            summary = "Schedule a Transfer",
            description = "Creates a new scheduled transfer using the provided transfer details."
    )
    public ResponseEntity<ApiDataResponseDto> scheduledTransfer(@Valid @RequestBody ScheduleTransferDto scheduleTransferDto) {
        return new ResponseEntity<>(scheduledTransferService.scheduledTransfer(scheduleTransferDto), HttpStatus.CREATED);
    }

    @GetMapping("/{senderAccountId}")
    @Operation(
            summary = "Retrieve Scheduled Transfers for a User",
            description = "Fetches all scheduled transfers linked to the provided sender account ID with pagination."
    )
    public ResponseEntity<ApiDataResponseDto> getUserScheduledTransfers(
            @PathVariable String senderAccountId,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize) {

        ApiDataResponseDto response = scheduledTransferService.getScheduledTransfers(senderAccountId, page, pageSize);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/cancel/{transferId}")
    @Operation(
            summary = "Cancel Scheduled Transfer for a User"
    )
    public ResponseEntity<ApiDataResponseDto> cancelUserScheduledTransfer(@PathVariable String transferId) {
        ApiDataResponseDto response = scheduledTransferService.cancelUserScheduledTransfer(transferId.trim());
        return ResponseEntity.ok(response);
    }
}
