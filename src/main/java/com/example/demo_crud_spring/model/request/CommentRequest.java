package com.example.demo_crud_spring.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CommentRequest {

    @NotBlank(message = "Content is required")
    private String content;

    @NotBlank(message = "Commenter name is required")
    private String commenterName;
}
