package com.example.demo_crud_spring.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CommentDto {

    private Long id;
    private String content;
    private String commenterName;
    private Long postId;
    private LocalDateTime createdAt;
}
