package com.gdsc.solutionChallenge.posts.repository;

import com.gdsc.solutionChallenge.posts.entity.Post;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PostRepository extends JpaRepository<Post, Long> {
    Optional<Post> findTop1ByGradeOrderByLikesDesc(Integer grade);

    @Query(value = "SELECT * FROM course_info WHERE grade = ?1 ORDER BY created_date DESC LIMIT ?2", nativeQuery = true)
    List<Post> findTopNByGradeOrderByCreatedDateDesc(Integer grade, Integer n);
}
