package com.example.demo_crud_spring.model.request;

import com.example.demo_crud_spring.model.entity.ReactionType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReactionRequest {

    @NotNull(message = "User id is required")
    private Long userId;

    @NotNull(message = "Reaction type is required")
    private ReactionType reactionType;
}
