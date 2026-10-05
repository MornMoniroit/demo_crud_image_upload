package com.example.demo_crud_spring.messaging;

import com.example.demo_crud_spring.config.RabbitMQConfig;
import com.example.demo_crud_spring.model.event.UserRegisteredEvent;
import com.example.demo_crud_spring.service.EmailSender;
import com.example.demo_crud_spring.service.SmsSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationListener {

    private final EmailSender emailSender;
    private final SmsSender smsSender;

    @RabbitListener(queues = RabbitMQConfig.QUEUE)
    public void handleUserRegistered(UserRegisteredEvent event) {
        log.info("Processing user-registered notification for user {}", event.getUserId());

        emailSender.sendWelcomeEmail(event.getEmail(), event.getFirstName());

        if (StringUtils.hasText(event.getPhoneNumber())) {
            smsSender.sendWelcomeSms(event.getPhoneNumber(), event.getFirstName());
        }
    }
}
