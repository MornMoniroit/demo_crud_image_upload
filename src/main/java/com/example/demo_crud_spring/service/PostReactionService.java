package com.example.demo_crud_spring.service;

import com.example.demo_crud_spring.model.dto.PostReactionDto;
import com.example.demo_crud_spring.model.dto.ReactionSummaryDto;
import com.example.demo_crud_spring.model.request.ReactionRequest;

public interface PostReactionService {

    PostReactionDto react(Long postId, ReactionRequest request);

    void removeReaction(Long postId, Long userId);

    ReactionSummaryDto getSummary(Long postId, Long userId);
}
