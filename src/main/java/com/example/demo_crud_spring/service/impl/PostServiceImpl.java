package com.example.demo_crud_spring.service.impl;

import com.example.demo_crud_spring.exception.ResourceNotFoundException;
import com.example.demo_crud_spring.model.dto.PostDto;
import com.example.demo_crud_spring.model.entity.Post;
import com.example.demo_crud_spring.model.entity.User;
import com.example.demo_crud_spring.model.request.PostRequest;
import com.example.demo_crud_spring.model.response.PageResponse;
import com.example.demo_crud_spring.repository.CommentRepository;
import com.example.demo_crud_spring.repository.PostRepository;
import com.example.demo_crud_spring.repository.UserRepository;
import com.example.demo_crud_spring.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public PostDto createPost(PostRequest request) {
        User author = userRepository.findById(request.getAuthorId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + request.getAuthorId()));

        Post post = new Post();
        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        post.setAuthor(author);

        return toDto(postRepository.save(post));
    }

    @Override
    @Transactional
    public PostDto updatePost(Long id, PostRequest request) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + id));

        User author = userRepository.findById(request.getAuthorId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + request.getAuthorId()));

        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        post.setAuthor(author);

        return toDto(postRepository.save(post));
    }

    @Override
    public PostDto getPostById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + id));
        return toDto(post);
    }

    @Override
    public PageResponse<PostDto> getAllPosts(Pageable pageable) {
        Page<PostDto> page = postRepository.findAll(pageable).map(this::toDto);
        return PageResponse.from(page);
    }

    @Override
    @Transactional
    public void deletePost(Long id) {
        if (!postRepository.existsById(id)) {
            throw new ResourceNotFoundException("Post not found with id: " + id);
        }
        commentRepository.deleteByPostId(id);
        postRepository.deleteById(id);
    }

    private PostDto toDto(Post post) {
        User author = post.getAuthor();
        String authorName = author.getFirstName() + " " + author.getLastName();
        return new PostDto(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                author.getId(),
                authorName,
                post.getCreatedAt(),
                post.getUpdatedAt()
        );
    }
}
