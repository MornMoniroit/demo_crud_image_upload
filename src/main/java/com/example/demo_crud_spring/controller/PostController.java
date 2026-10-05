package com.example.demo_crud_spring.controller;

import com.example.demo_crud_spring.model.dto.PostDto;
import com.example.demo_crud_spring.model.request.PostRequest;
import com.example.demo_crud_spring.model.response.ApiResponse;
import com.example.demo_crud_spring.model.response.PageResponse;
import com.example.demo_crud_spring.service.PostService;
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
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<ApiResponse<PostDto>> createPost(@Valid @RequestBody PostRequest request) {
        PostDto created = postService.createPost(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>("Post created successfully", created, LocalDateTime.now()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<PostDto>> updatePost(@PathVariable Long id,
                                                            @Valid @RequestBody PostRequest request) {
        PostDto updated = postService.updatePost(id, request);
        return ResponseEntity.ok(new ApiResponse<>("Post updated successfully", updated, LocalDateTime.now()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PostDto>> getPostById(@PathVariable Long id) {
        PostDto post = postService.getPostById(id);
        return ResponseEntity.ok(new ApiResponse<>("Post retrieved successfully", post, LocalDateTime.now()));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<PostDto>>> getAllPosts(
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        PageResponse<PostDto> posts = postService.getAllPosts(pageable);
        return ResponseEntity.ok(new ApiResponse<>("Posts retrieved successfully", posts, LocalDateTime.now()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.ok(new ApiResponse<>("Post deleted successfully", null, LocalDateTime.now()));
    }
}
