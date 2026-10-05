package com.example.demo_crud_spring.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class NotificationDto {

    private Long id;
    private String message;
    private Long relatedPostId;
    private Long relatedCommentId;
    private boolean read;
    private LocalDateTime createdAt;
}
