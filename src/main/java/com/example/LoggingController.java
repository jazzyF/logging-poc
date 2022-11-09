package com.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.example.LoggingRequestFilter.CORRELATION_ID;
import static com.example.LoggingRequestFilter.LOGGED_IN_USER;

@RestController
@RequestMapping("/api/v1/log")
public class LoggingController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingController.class);

    @RequestMapping
    public String log(@RequestAttribute("filterUUID") String filterUUID) {
        LOGGER.info("Invoked log endpoint at {}ns", System.nanoTime());
        LOGGER.info("Got from request correlation Id: {}", filterUUID);
        LOGGER.info("From MDC -> Correlation Identifier: {}", MDC.get(CORRELATION_ID));
        LOGGER.info("User Identifier: {}", MDC.get(LOGGED_IN_USER));
        MDC.clear();
        return "logging at : " + System.currentTimeMillis();
    }

}
