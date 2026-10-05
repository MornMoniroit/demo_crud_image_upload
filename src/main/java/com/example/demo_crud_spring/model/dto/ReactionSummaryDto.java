package com.example.demo_crud_spring.model.dto;

import com.example.demo_crud_spring.model.entity.ReactionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReactionSummaryDto {

    private Map<ReactionType, Long> counts;
    private long totalCount;
    private ReactionType myReaction;
}
