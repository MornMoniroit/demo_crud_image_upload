package com.example.demo_crud_spring.controller;

import com.example.demo_crud_spring.model.dto.NotificationDto;
import com.example.demo_crud_spring.model.response.ApiResponse;
import com.example.demo_crud_spring.model.response.PageResponse;
import com.example.demo_crud_spring.service.UserNotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api/users/{userId}/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final UserNotificationService userNotificationService;

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<NotificationDto>>> getNotifications(
            @PathVariable Long userId,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        PageResponse<NotificationDto> notifications = userNotificationService.getNotifications(userId, pageable);
        return ResponseEntity.ok(new ApiResponse<>("Notifications retrieved successfully", notifications, LocalDateTime.now()));
    }

    @GetMapping("/unread-count")
    public ResponseEntity<ApiResponse<Map<String, Long>>> getUnreadCount(@PathVariable Long userId) {
        long count = userNotificationService.getUnreadCount(userId);
        return ResponseEntity.ok(new ApiResponse<>("Unread count retrieved successfully", Map.of("unreadCount", count), LocalDateTime.now()));
    }

    @PatchMapping("/{notificationId}/read")
    public ResponseEntity<ApiResponse<NotificationDto>> markAsRead(@PathVariable Long userId,
                                                                     @PathVariable Long notificationId) {
        NotificationDto updated = userNotificationService.markAsRead(userId, notificationId);
        return ResponseEntity.ok(new ApiResponse<>("Notification marked as read", updated, LocalDateTime.now()));
    }
}
