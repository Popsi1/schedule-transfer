package com.example.schedulefundtransfer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.time.LocalDateTime;

@SpringBootApplication
@EnableScheduling
@EnableAsync
@EnableCaching
public class ScheduleFundTransferApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScheduleFundTransferApplication.class, args);
    }

}
