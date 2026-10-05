package com.example.demo_crud_spring.service.impl;

import com.example.demo_crud_spring.exception.ResourceNotFoundException;
import com.example.demo_crud_spring.model.dto.PostReactionDto;
import com.example.demo_crud_spring.model.dto.ReactionSummaryDto;
import com.example.demo_crud_spring.model.entity.Post;
import com.example.demo_crud_spring.model.entity.PostReaction;
import com.example.demo_crud_spring.model.entity.ReactionType;
import com.example.demo_crud_spring.model.entity.User;
import com.example.demo_crud_spring.model.request.ReactionRequest;
import com.example.demo_crud_spring.repository.PostReactionRepository;
import com.example.demo_crud_spring.repository.PostRepository;
import com.example.demo_crud_spring.repository.UserRepository;
import com.example.demo_crud_spring.service.PostReactionService;
import com.example.demo_crud_spring.service.UserNotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.EnumMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PostReactionServiceImpl implements PostReactionService {

    private final PostReactionRepository postReactionRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final UserNotificationService userNotificationService;

    @Override
    @Transactional
    public PostReactionDto react(Long postId, ReactionRequest request) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + postId));
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + request.getUserId()));

        return postReactionRepository.findByPostIdAndUserId(postId, request.getUserId())
                .map(existing -> {
                    existing.setReactionType(request.getReactionType());
                    return toDto(postReactionRepository.save(existing));
                })
                .orElseGet(() -> {
                    PostReaction reaction = new PostReaction();
                    reaction.setPost(post);
                    reaction.setUser(user);
                    reaction.setReactionType(request.getReactionType());

                    PostReaction saved = postReactionRepository.save(reaction);
                    userNotificationService.notifyNewReaction(post, saved);

                    return toDto(saved);
                });
    }

    @Override
    @Transactional
    public void removeReaction(Long postId, Long userId) {
        PostReaction reaction = postReactionRepository.findByPostIdAndUserId(postId, userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Reaction not found for post: " + postId + " and user: " + userId));
        postReactionRepository.delete(reaction);
    }

    @Override
    public ReactionSummaryDto getSummary(Long postId, Long userId) {
        if (!postRepository.existsById(postId)) {
            throw new ResourceNotFoundException("Post not found with id: " + postId);
        }

        Map<ReactionType, Long> counts = new EnumMap<>(ReactionType.class);
        for (ReactionType type : ReactionType.values()) {
            counts.put(type, 0L);
        }
        for (PostReactionRepository.ReactionCount row : postReactionRepository.countByPostIdGroupByType(postId)) {
            counts.put(row.getReactionType(), row.getCount());
        }

        long total = counts.values().stream().mapToLong(Long::longValue).sum();

        ReactionType myReaction = null;
        if (userId != null) {
            myReaction = postReactionRepository.findByPostIdAndUserId(postId, userId)
                    .map(PostReaction::getReactionType)
                    .orElse(null);
        }

        return new ReactionSummaryDto(counts, total, myReaction);
    }

    private PostReactionDto toDto(PostReaction reaction) {
        return new PostReactionDto(
                reaction.getId(),
                reaction.getPost().getId(),
                reaction.getUser().getId(),
                reaction.getReactionType(),
                reaction.getCreatedAt()
        );
    }
}
