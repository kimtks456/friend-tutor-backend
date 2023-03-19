package com.gdsc.solutionChallenge.posts.repository;

import com.gdsc.solutionChallenge.posts.entity.Likes;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikesRepository extends JpaRepository<Likes, Long> {
    Optional<Likes> findByPostIdAndMemberId(Long postId, Long memberId);
}
