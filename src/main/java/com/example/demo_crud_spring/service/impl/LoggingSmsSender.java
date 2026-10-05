package com.example.demo_crud_spring.service.impl;

import com.example.demo_crud_spring.service.SmsSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Placeholder implementation: logs instead of calling a real provider (Twilio/SNS/etc).
 * Sending a real SMS means handing user PII to an external SaaS, which needs admin
 * authorization per company policy before a real client is wired in here.
 */
@Slf4j
@Service
public class LoggingSmsSender implements SmsSender {

    @Override
    public void sendWelcomeSms(String phoneNumber, String firstName) {
        log.info("[STUB] Would send welcome SMS to {} (name: {})", phoneNumber, firstName);
    }
}
