package com.gdsc.solutionChallenge.posts.service;


import com.gdsc.solutionChallenge.global.utils.SecurityUtil;
import com.gdsc.solutionChallenge.member.entity.Member;
import com.gdsc.solutionChallenge.member.repository.MemberRepository;
import com.gdsc.solutionChallenge.posts.dto.req.PostSaveDto;
import com.gdsc.solutionChallenge.posts.dto.res.FullPost;
import com.gdsc.solutionChallenge.posts.dto.res.SummPost;
import com.gdsc.solutionChallenge.posts.entity.Post;
import com.gdsc.solutionChallenge.posts.repository.PostRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional(readOnly = false)
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    public SummPost createPost(PostSaveDto postSaveDto) {

        Post post = postSaveDto.toEntity();
        post.confirmWriter(memberRepository.findByUsername(SecurityUtil.getLoginUsername())
                .orElseThrow(() -> new IllegalArgumentException("로그인된 사용자가 DB에 없습니다.")));

        Post savedPost = postRepository.save(post);
        Member member = memberRepository.findByUsername(SecurityUtil.getLoginUsername()).get();
        member.addScore(10);
        memberRepository.save(member);

        return SummPost.builder()
                .course_id(savedPost.getId())
                .writer(savedPost.getWriter().getNickName())
                .title(savedPost.getTitle())
                .video_id(savedPost.getVideo_id())
                .likes(savedPost.getLikes())
                .build();
    }

    public List<FullPost> getAllPosts() {
        List<Post> posts = postRepository.findAll();

        if (posts.isEmpty()) {
            throw new IllegalArgumentException("게시글이 하나도 없습니다.");
        }

        List<FullPost> fullPostList = new ArrayList<>();
        for (Post post : posts) {
            fullPostList.add(FullPost.builder()
                            .course_id(post.getId())
                            .grade(post.getGrade())
                            .subject(post.getSubject())
                            .writer(post.getWriter().getNickName())
                            .title(post.getTitle())
                            .description(post.getDescription())
                            .video_id(post.getVideo_id())
                            .drive_link(post.getDrive_link())
                            .likes(post.getLikes())
                            .created_at(post.getCreatedDate().toString())
                            .build());
        }
        return fullPostList;
    }

    public FullPost getFullPost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));
        return FullPost.builder()
                .course_id(post.getId())
                .grade(post.getGrade())
                .subject(post.getSubject())
                .writer(post.getWriter().getNickName())
                .title(post.getTitle())
                .description(post.getDescription())
                .video_id(post.getVideo_id())
                .drive_link(post.getDrive_link())
                .likes(post.getLikes())
                .created_at(post.getCreatedDate().toString())
                .build();
    }

    public SummPost getTopTrendingSummPost(Integer grade) {
        Post post = postRepository.findTop1ByGradeOrderByLikesDesc(grade).orElseThrow(() -> new IllegalArgumentException("해당 학년의 게시글이 없습니다."));
        return SummPost.builder()
                .course_id(post.getId())
                .writer(post.getWriter().getNickName())
                .title(post.getTitle())
                .video_id(post.getVideo_id())
                .likes(post.getLikes())
                .build();
    }

    public List<SummPost> getRecentSummPosts(Integer grade, Integer number) {
        List<Post> posts = postRepository.findTopNByGradeOrderByCreatedDateDesc(grade, number);

        if (posts.isEmpty()) {
            throw new IllegalArgumentException("해당 학년의 게시글이 하나도 없습니다.");
        }

        List<SummPost> summPosts = new ArrayList<>();
        for (Post post : posts) {
            summPosts.add(SummPost.builder()
                    .course_id(post.getId())
                    .writer(post.getWriter().getNickName())
                    .title(post.getTitle())
                    .video_id(post.getVideo_id())
                    .likes(post.getLikes())
                    .build());
        }
        return summPosts;
    }
}
