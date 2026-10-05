package com.example.demo_crud_spring.controller;

import com.example.demo_crud_spring.model.dto.PostReactionDto;
import com.example.demo_crud_spring.model.dto.ReactionSummaryDto;
import com.example.demo_crud_spring.model.request.ReactionRequest;
import com.example.demo_crud_spring.model.response.ApiResponse;
import com.example.demo_crud_spring.service.PostReactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/posts/{postId}/reactions")
@RequiredArgsConstructor
public class PostReactionController {

    private final PostReactionService postReactionService;

    @PutMapping
    public ResponseEntity<ApiResponse<PostReactionDto>> react(@PathVariable Long postId,
                                                                @Valid @RequestBody ReactionRequest request) {
        PostReactionDto reaction = postReactionService.react(postId, request);
        return ResponseEntity.ok(new ApiResponse<>("Reaction saved successfully", reaction, LocalDateTime.now()));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse<Void>> removeReaction(@PathVariable Long postId, @PathVariable Long userId) {
        postReactionService.removeReaction(postId, userId);
        return ResponseEntity.ok(new ApiResponse<>("Reaction removed successfully", null, LocalDateTime.now()));
    }

    @GetMapping("/summary")
    public ResponseEntity<ApiResponse<ReactionSummaryDto>> getSummary(
            @PathVariable Long postId,
            @RequestParam(required = false) Long userId) {
        ReactionSummaryDto summary = postReactionService.getSummary(postId, userId);
        return ResponseEntity.ok(new ApiResponse<>("Reaction summary retrieved successfully", summary, LocalDateTime.now()));
    }
}
