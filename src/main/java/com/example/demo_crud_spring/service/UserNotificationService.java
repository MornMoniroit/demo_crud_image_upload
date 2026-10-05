package com.example.demo_crud_spring.service;

import com.example.demo_crud_spring.model.dto.NotificationDto;
import com.example.demo_crud_spring.model.entity.Comment;
import com.example.demo_crud_spring.model.entity.Post;
import com.example.demo_crud_spring.model.response.PageResponse;
import org.springframework.data.domain.Pageable;

public interface UserNotificationService {

    void notifyNewComment(Post post, Comment comment);

    PageResponse<NotificationDto> getNotifications(Long userId, Pageable pageable);

    long getUnreadCount(Long userId);

    NotificationDto markAsRead(Long userId, Long notificationId);
}
