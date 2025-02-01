package com.example.schedulefundtransfer.repository;

import com.example.schedulefundtransfer.entity.ScheduledTransfer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
@Repository
public interface ScheduledTransferRepository extends JpaRepository<ScheduledTransfer, UUID> {

    // Find transfers by status and schedule date before the current time
    @Query("SELECT t FROM ScheduledTransfer t WHERE t.status = :status AND t.transferDate < :scheduleDate")
    Page<ScheduledTransfer> findByStatusAndScheduleDateBefore(@Param("status") String status,
                                                              @Param("scheduleDate") LocalDateTime scheduleDate,
                                                              Pageable pageable);

    @Query("SELECT t FROM ScheduledTransfer t WHERE t.status = :status")
    List<ScheduledTransfer> findByStatus(@Param("status") String status);

    @Query("SELECT u FROM ScheduledTransfer u WHERE u.senderAccountId = :senderAccountId ORDER BY u.createdAt DESC")
    Page<ScheduledTransfer> findBySenderAccountId(@Param("senderAccountId") String senderAccountId, Pageable pageable);

}