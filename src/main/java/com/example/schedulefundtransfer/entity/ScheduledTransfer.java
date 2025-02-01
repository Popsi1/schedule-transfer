package com.example.schedulefundtransfer.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScheduledTransfer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private UUID transferId;

    @Column(nullable = false)
    private String senderAccountId;

    @Column(nullable = false)
    private String recipientAccountId;

    @Column(nullable = false)
    private BigDecimal transferAmount;

    @Column(nullable = false)
    private LocalDateTime transferDate;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false, columnDefinition = "int default 0")
    private int retryCount;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;
}
