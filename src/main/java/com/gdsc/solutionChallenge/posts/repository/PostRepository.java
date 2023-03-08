package com.gdsc.solutionChallenge.posts.repository;

import com.gdsc.solutionChallenge.posts.entity.Post;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
    Optional<Post> findTop1ByGradeOrderByLikesDesc(Integer grade);
}
