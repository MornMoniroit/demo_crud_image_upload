package com.example.demo_crud_spring.controller;

import com.example.demo_crud_spring.model.dto.CommentDto;
import com.example.demo_crud_spring.model.request.CommentRequest;
import com.example.demo_crud_spring.model.response.ApiResponse;
import com.example.demo_crud_spring.model.response.PageResponse;
import com.example.demo_crud_spring.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/posts/{postId}/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<ApiResponse<CommentDto>> addComment(@PathVariable Long postId,
                                                                @Valid @RequestBody CommentRequest request) {
        CommentDto created = commentService.addComment(postId, request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>("Comment added successfully", created, LocalDateTime.now()));
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<ApiResponse<CommentDto>> updateComment(@PathVariable Long postId,
                                                                   @PathVariable Long commentId,
                                                                   @Valid @RequestBody CommentRequest request) {
        CommentDto updated = commentService.updateComment(postId, commentId, request);
        return ResponseEntity.ok(new ApiResponse<>("Comment updated successfully", updated, LocalDateTime.now()));
    }

    @GetMapping("/{commentId}")
    public ResponseEntity<ApiResponse<CommentDto>> getComment(@PathVariable Long postId,
                                                                @PathVariable Long commentId) {
        CommentDto comment = commentService.getComment(postId, commentId);
        return ResponseEntity.ok(new ApiResponse<>("Comment retrieved successfully", comment, LocalDateTime.now()));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<CommentDto>>> getComments(
            @PathVariable Long postId,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.ASC) Pageable pageable) {
        PageResponse<CommentDto> comments = commentService.getCommentsByPost(postId, pageable);
        return ResponseEntity.ok(new ApiResponse<>("Comments retrieved successfully", comments, LocalDateTime.now()));
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<ApiResponse<Void>> deleteComment(@PathVariable Long postId,
                                                             @PathVariable Long commentId) {
        commentService.deleteComment(postId, commentId);
        return ResponseEntity.ok(new ApiResponse<>("Comment deleted successfully", null, LocalDateTime.now()));
    }
}
