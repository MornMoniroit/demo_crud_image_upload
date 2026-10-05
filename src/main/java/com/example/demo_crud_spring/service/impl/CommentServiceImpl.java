package com.example.demo_crud_spring.service.impl;

import com.example.demo_crud_spring.exception.ResourceNotFoundException;
import com.example.demo_crud_spring.model.dto.CommentDto;
import com.example.demo_crud_spring.model.entity.Comment;
import com.example.demo_crud_spring.model.entity.Post;
import com.example.demo_crud_spring.model.request.CommentRequest;
import com.example.demo_crud_spring.model.response.PageResponse;
import com.example.demo_crud_spring.repository.CommentRepository;
import com.example.demo_crud_spring.repository.PostRepository;
import com.example.demo_crud_spring.service.CommentService;
import com.example.demo_crud_spring.service.UserNotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserNotificationService userNotificationService;

    @Override
    @Transactional
    public CommentDto addComment(Long postId, CommentRequest request) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + postId));

        Comment comment = new Comment();
        comment.setContent(request.getContent());
        comment.setCommenterName(request.getCommenterName());
        comment.setPost(post);

        Comment saved = commentRepository.save(comment);
        userNotificationService.notifyNewComment(post, saved);

        return toDto(saved);
    }

    @Override
    @Transactional
    public CommentDto updateComment(Long postId, Long commentId, CommentRequest request) {
        Comment comment = commentRepository.findByIdAndPostId(commentId, postId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Comment not found with id: " + commentId + " for post: " + postId));

        comment.setContent(request.getContent());
        comment.setCommenterName(request.getCommenterName());

        return toDto(commentRepository.save(comment));
    }

    @Override
    public CommentDto getComment(Long postId, Long commentId) {
        Comment comment = commentRepository.findByIdAndPostId(commentId, postId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Comment not found with id: " + commentId + " for post: " + postId));
        return toDto(comment);
    }

    @Override
    public PageResponse<CommentDto> getCommentsByPost(Long postId, Pageable pageable) {
        if (!postRepository.existsById(postId)) {
            throw new ResourceNotFoundException("Post not found with id: " + postId);
        }
        Page<CommentDto> page = commentRepository.findByPostId(postId, pageable).map(this::toDto);
        return PageResponse.from(page);
    }

    @Override
    @Transactional
    public void deleteComment(Long postId, Long commentId) {
        Comment comment = commentRepository.findByIdAndPostId(commentId, postId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Comment not found with id: " + commentId + " for post: " + postId));
        commentRepository.delete(comment);
    }

    private CommentDto toDto(Comment comment) {
        return new CommentDto(
                comment.getId(),
                comment.getContent(),
                comment.getCommenterName(),
                comment.getPost().getId(),
                comment.getCreatedAt()
        );
    }
}
