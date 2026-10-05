package com.example.demo_crud_spring.service.impl;

import com.example.demo_crud_spring.exception.ResourceNotFoundException;
import com.example.demo_crud_spring.model.dto.NotificationDto;
import com.example.demo_crud_spring.model.entity.Comment;
import com.example.demo_crud_spring.model.entity.Notification;
import com.example.demo_crud_spring.model.entity.Post;
import com.example.demo_crud_spring.model.entity.PostReaction;
import com.example.demo_crud_spring.model.response.PageResponse;
import com.example.demo_crud_spring.repository.NotificationRepository;
import com.example.demo_crud_spring.repository.UserRepository;
import com.example.demo_crud_spring.service.UserNotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserNotificationServiceImpl implements UserNotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public void notifyNewComment(Post post, Comment comment) {
        Notification notification = new Notification();
        notification.setRecipient(post.getAuthor());
        notification.setMessage(comment.getCommenterName() + " commented on your post \"" + post.getTitle() + "\"");
        notification.setRelatedPostId(post.getId());
        notification.setRelatedCommentId(comment.getId());
        notification.setRead(false);

        notificationRepository.save(notification);
    }

    @Override
    @Transactional
    public void notifyNewReaction(Post post, PostReaction reaction) {
        if (post.getAuthor().getId().equals(reaction.getUser().getId())) {
            return;
        }

        String reactorName = reaction.getUser().getFirstName() + " " + reaction.getUser().getLastName();

        Notification notification = new Notification();
        notification.setRecipient(post.getAuthor());
        notification.setMessage(reactorName + " reacted " + reaction.getReactionType()
                + " to your post \"" + post.getTitle() + "\"");
        notification.setRelatedPostId(post.getId());
        notification.setRead(false);

        notificationRepository.save(notification);
    }

    @Override
    public PageResponse<NotificationDto> getNotifications(Long userId, Pageable pageable) {
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User not found with id: " + userId);
        }
        Page<NotificationDto> page = notificationRepository.findByRecipientId(userId, pageable).map(this::toDto);
        return PageResponse.from(page);
    }

    @Override
    public long getUnreadCount(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User not found with id: " + userId);
        }
        return notificationRepository.countByRecipientIdAndReadFalse(userId);
    }

    @Override
    @Transactional
    public NotificationDto markAsRead(Long userId, Long notificationId) {
        Notification notification = notificationRepository.findByIdAndRecipientId(notificationId, userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Notification not found with id: " + notificationId + " for user: " + userId));

        notification.setRead(true);
        return toDto(notificationRepository.save(notification));
    }

    private NotificationDto toDto(Notification notification) {
        return new NotificationDto(
                notification.getId(),
                notification.getMessage(),
                notification.getRelatedPostId(),
                notification.getRelatedCommentId(),
                notification.isRead(),
                notification.getCreatedAt()
        );
    }
}
