package com.example.demo_crud_spring.repository;

import com.example.demo_crud_spring.model.entity.PostReaction;
import com.example.demo_crud_spring.model.entity.ReactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostReactionRepository extends JpaRepository<PostReaction, Long> {

    Optional<PostReaction> findByPostIdAndUserId(Long postId, Long userId);

    void deleteByPostIdAndUserId(Long postId, Long userId);

    @Query("SELECT r.reactionType AS reactionType, COUNT(r) AS count " +
            "FROM PostReaction r WHERE r.post.id = :postId GROUP BY r.reactionType")
    List<ReactionCount> countByPostIdGroupByType(@Param("postId") Long postId);

    interface ReactionCount {
        ReactionType getReactionType();
        Long getCount();
    }
}
