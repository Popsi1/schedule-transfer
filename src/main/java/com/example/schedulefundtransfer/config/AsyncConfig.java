package com.example.schedulefundtransfer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class AsyncConfig {

    @Bean
    public ThreadPoolTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);  // Initial pool size
        executor.setMaxPoolSize(10);  // Maximum pool size
        executor.setQueueCapacity(50);  // Queue capacity for pending tasks
        executor.setThreadNamePrefix("TransferRetry-");
        executor.initialize();
        return executor;
    }
}
