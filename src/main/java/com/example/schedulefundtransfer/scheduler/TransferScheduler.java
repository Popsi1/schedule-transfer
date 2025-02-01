//package com.example.schedulefundtransfer.scheduler;
//
//import com.example.schedulefundtransfer.entity.ScheduledTransfer;
//import com.example.schedulefundtransfer.repository.ScheduledTransferRepository;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.scheduling.annotation.Async;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.concurrent.CompletableFuture;
//
//@Slf4j
//@Component
//@RequiredArgsConstructor
//public class TransferScheduler {
//
//    private final ScheduledTransferRepository transferRepository;
//
//    public static final int MAX_RETRY_COUNT = 3; // Max retry attempts
//
//    //1. Runs every minute to process urgent transactions
//    @Scheduled(fixedRate = 60000) // Every 1 minute
//    public void processUrgentTransfers() {
//        log.info("Starting real-time transfer processing...");
//
//        int page = 0;
//        int pageSize = 50; // Process in small batches
//        Page<ScheduledTransfer> pendingTransfers;
//
//        do {
//            pendingTransfers = transferRepository.findByStatusAndScheduleDateBefore(
//                    "PENDING", LocalDateTime.now(), PageRequest.of(page, pageSize));
//
//            if (pendingTransfers.isEmpty()) {
//                log.info("No urgent transfers to process.");
//                return;
//            }
//
//            for (ScheduledTransfer transfer : pendingTransfers) {
//                CompletableFuture.runAsync(() -> executeTransferAsync(transfer));
//            }
//
//            page++; // Move to the next batch
//        } while (pendingTransfers.hasNext());
//
//        log.info("Real-time transfer processing completed.");
//    }
//
//    /**
//     * 2. Daily batch job: Runs at midnight to retry failed transfers
//     */
//    @Scheduled(cron = "0 0 0 * * *") // Runs daily at midnight
//    public void retryFailedTransfers() {
//        log.info("Starting daily batch job for failed transfers...");
//
//        List<ScheduledTransfer> failedTransfers = transferRepository.findByStatus("FAILED");
//
//        if (failedTransfers.isEmpty()) {
//            log.info("No failed transfers to retry.");
//            return;
//        }
//
//        for (ScheduledTransfer transfer : failedTransfers) {
//            CompletableFuture.runAsync(() -> executeTransferAsync(transfer));
//        }
//
//        log.info("Daily batch job completed.");
//    }
//
//    /**
//     * Asynchronous execution to avoid blocking the scheduler
//     */
//    @Async
//    public void executeTransferAsync(ScheduledTransfer transfer) {
//        if (transfer.getRetryCount() >= MAX_RETRY_COUNT) {
//            log.warn("Transfer ID {} reached max retry count and will not be retried.", transfer.getTransferId());
//            transfer.setStatus("FAILED");
//            transferRepository.save(transfer);
//            return;
//        }
//
//        try {
//            // Simulate delay before retry (e.g., exponential backoff)
//            int delay = (int) Math.pow(2, transfer.getRetryCount()) * 1000;  // Exponential backoff
//            Thread.sleep(delay);  // Delay before retrying
//
//            executeTransfer(transfer);
//            transfer.setStatus("SUCCESS");
//            log.info("Transfer ID {} processed successfully.", transfer.getTransferId());
//        } catch (Exception e) {
//            transfer.setRetryCount(transfer.getRetryCount() + 1);
//            transfer.setStatus("FAILED");
//            log.error("Transfer ID {} failed (Attempt {}): {}", transfer.getTransferId(), transfer.getRetryCount(), e.getMessage());
//        }
//        transferRepository.save(transfer);
//    }
//
//
//    private void executeTransfer(ScheduledTransfer transfer) {
//        // Call Payment API or Transaction Processing Service
//    }
//}
