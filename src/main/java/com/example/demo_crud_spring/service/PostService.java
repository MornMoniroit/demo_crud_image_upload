package com.example.demo_crud_spring.service;

import com.example.demo_crud_spring.model.dto.PostDto;
import com.example.demo_crud_spring.model.request.PostRequest;
import com.example.demo_crud_spring.model.response.PageResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface PostService {

    PostDto createPost(PostRequest request);

    PostDto updatePost(Long id, PostRequest request);

    PostDto getPostById(Long id);

    PageResponse<PostDto> getAllPosts(Pageable pageable);

    void deletePost(Long id);

    PostDto uploadPostImage(Long id, MultipartFile image);
}
