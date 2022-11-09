package com.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import static com.example.LoggingRequestFilter.LOGGED_IN_USER;
import static com.example.LoggingRequestFilter.SYSTEM_USER;

@Service
public class LoggingService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingService.class);

    @Scheduled(cron = "${app.cron}")
    public void generateLog() {
        String generatedLog = "Saying hello at " + System.currentTimeMillis();
        MDC.put(LOGGED_IN_USER, SYSTEM_USER);
        LOGGER.info("Generated: {} with {}", generatedLog, SYSTEM_USER);
        MDC.clear();
    }

}
