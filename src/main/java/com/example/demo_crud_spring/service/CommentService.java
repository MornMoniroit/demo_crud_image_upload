package com.example.demo_crud_spring.service;

import com.example.demo_crud_spring.model.dto.CommentDto;
import com.example.demo_crud_spring.model.request.CommentRequest;
import com.example.demo_crud_spring.model.response.PageResponse;
import org.springframework.data.domain.Pageable;

public interface CommentService {

    CommentDto addComment(Long postId, CommentRequest request);

    CommentDto updateComment(Long postId, Long commentId, CommentRequest request);

    CommentDto getComment(Long postId, Long commentId);

    PageResponse<CommentDto> getCommentsByPost(Long postId, Pageable pageable);

    void deleteComment(Long postId, Long commentId);
}
