package com.example.demo_crud_spring.service.impl;

import com.example.demo_crud_spring.config.RabbitMQConfig;
import com.example.demo_crud_spring.model.dto.UserDto;
import com.example.demo_crud_spring.model.event.UserRegisteredEvent;
import com.example.demo_crud_spring.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final RabbitTemplate rabbitTemplate;

    @Override
    @Async("notificationExecutor")
    public void notifyUserRegistered(UserDto user) {
        UserRegisteredEvent event = new UserRegisteredEvent(
                user.getId(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getFirstName(),
                user.getLastName()
        );

        try {
            rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, RabbitMQConfig.ROUTING_KEY, event);
        } catch (Exception e) {
            // Notification delivery must never fail user registration itself;
            // this runs on a separate async thread after the user is already saved.
            log.error("Failed to publish user-registered notification for user {}", user.getId(), e);
        }
    }
}
