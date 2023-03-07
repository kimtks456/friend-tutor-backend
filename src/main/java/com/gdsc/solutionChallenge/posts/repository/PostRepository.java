package com.gdsc.solutionChallenge.posts.repository;

import com.gdsc.solutionChallenge.posts.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
