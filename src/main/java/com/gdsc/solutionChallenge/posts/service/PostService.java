package com.gdsc.solutionChallenge.posts.service;


import com.gdsc.solutionChallenge.global.utils.SecurityUtil;
import com.gdsc.solutionChallenge.member.entity.Member;
import com.gdsc.solutionChallenge.member.repository.MemberRepository;
import com.gdsc.solutionChallenge.posts.Score;
import com.gdsc.solutionChallenge.posts.dto.req.PostSaveDto;
import com.gdsc.solutionChallenge.posts.dto.res.FullPost;
import com.gdsc.solutionChallenge.posts.dto.res.SummPost;
import com.gdsc.solutionChallenge.posts.entity.Likes;
import com.gdsc.solutionChallenge.posts.entity.Post;
import com.gdsc.solutionChallenge.posts.repository.LikesRepository;
import com.gdsc.solutionChallenge.posts.repository.PostRepository;
import java.util.ArrayList;
import java.util.Arrays;
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
    private final LikesRepository likesRepository;
    public final static List<String> SUBJECTS = Arrays.asList("math", "korean", "english", "science", "other");

    public SummPost createPost(PostSaveDto postSaveDto) {
        Post post;
        try {
            post = postSaveDto.toEntity();
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }

        post.confirmWriter(memberRepository.findByUsername(SecurityUtil.getLoginUsername())
                .orElseThrow(() -> new IllegalArgumentException("로그인된 사용자가 DB에 없습니다.")));

        Post savedPost = postRepository.save(post);
        Member member = memberRepository.findByUsername(SecurityUtil.getLoginUsername()).get();
        member.updateScore(Score.post.getScore());
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
        Long memberId = memberRepository.findByUsername(SecurityUtil.getLoginUsername()).get().getId();
        boolean isLiked = likesRepository.findByPostIdAndMemberId(id, memberId).isPresent();
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
                .is_liked(isLiked)
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

    public List<String> getSubjects() {
        return SUBJECTS;
    }

    public boolean likePost(Long courseId) {
        Post post = postRepository.findById(courseId).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));
        Member writer = memberRepository.findById(post.getWriter().getId()).get();
        Member liker = memberRepository.findByUsername(SecurityUtil.getLoginUsername()).get();
        Likes likes = likesRepository.findByPostIdAndMemberId(courseId, liker.getId()).orElse(null);

        if (likes == null) {
            Likes newlikes = new Likes();
            newlikes.confirmMemberPost(liker, post);
            writer.updateScore(Score.like.getScore());
            post.updateLikes(1);
            likesRepository.save(newlikes);
            postRepository.save(post);
            memberRepository.save(writer);
            return true;
        } else {
            likesRepository.delete(likes);
            writer.updateScore(Score.dislike.getScore());
            post.updateLikes(-1);
            memberRepository.save(writer);
            postRepository.save(post);
            return false;
        }
    }

    public List<SummPost> getMyLikePosts() {
        Member member = memberRepository.findByUsername(SecurityUtil.getLoginUsername()).get();
        List<Likes> likes = likesRepository.findAllByMemberId(member.getId());

        if (likes.isEmpty()) {
            return new ArrayList<>();
        }

        List<SummPost> summPosts = new ArrayList<>();
        for (Likes like : likes) {
            Post post = postRepository.findById(like.getPost().getId()).get();
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

//    public String getSubjectRegExp() {
//        return SUBJECTS.stream()
//                .map(s -> "(" + s + ")")
//                .reduce((s1, s2) -> s1 + "|" + s2)
//                .orElse("");
//    }
}
