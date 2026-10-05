package com.example.demo_crud_spring.model.dto;

import com.example.demo_crud_spring.model.entity.ReactionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PostReactionDto {

    private Long id;
    private Long postId;
    private Long userId;
    private ReactionType reactionType;
    private LocalDateTime createdAt;
}
