package com.example.demo_crud_spring.service.impl;

import com.example.demo_crud_spring.service.EmailSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Placeholder implementation: logs instead of calling a real provider (SMTP/SES/SendGrid).
 * Sending real email means handing user PII to an external SaaS, which needs admin
 * authorization per company policy before a real client is wired in here.
 */
@Slf4j
@Service
public class LoggingEmailSender implements EmailSender {

    @Override
    public void sendWelcomeEmail(String toEmail, String firstName) {
        log.info("[STUB] Would send welcome email to {} (name: {})", toEmail, firstName);
    }
}
