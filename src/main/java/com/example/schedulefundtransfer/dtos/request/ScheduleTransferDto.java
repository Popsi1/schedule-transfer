package com.example.schedulefundtransfer.dtos.request;

import com.example.schedulefundtransfer.validation.FutureDate;
import com.example.schedulefundtransfer.validation.ValidDateFormat;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ScheduleTransferDto {

    @NotBlank(message = "Sender account ID cannot be blank")
    @Pattern(regexp = "\\d{10}", message = "Sender account ID must be exactly 10 characters long")
    private String senderAccountId;

    @NotBlank(message = "Recipient account ID cannot be blank")
    @Pattern(regexp = "\\d{10}", message = "Recipient account ID must be exactly 10 characters long")
    private String recipientAccountId;

    @Min(value = 1, message = "Transfer amount must be positive")
    private BigDecimal transferAmount;


    @ValidDateFormat
    @FutureDate
    @NotBlank(message = "Transfer date cannot be null")
    private String transferDate;

}


