package com.gdsc.solutionChallenge.posts.service;

import com.gdsc.solutionChallenge.global.utils.SecurityUtil;
import com.gdsc.solutionChallenge.member.entity.Member;
import com.gdsc.solutionChallenge.member.repository.MemberRepository;
import com.gdsc.solutionChallenge.posts.dto.res.CertificateInfo;
import com.gdsc.solutionChallenge.posts.dto.res.CheckCertificateRes;
import com.gdsc.solutionChallenge.posts.entity.Certificate;
import com.gdsc.solutionChallenge.posts.entity.Post;
import com.gdsc.solutionChallenge.posts.repository.CertificateRepository;
import com.gdsc.solutionChallenge.posts.repository.PostRepository;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CertificateService {
    private final MemberRepository memberRepository;
    private final CertificateRepository certificateRepository;

    public CheckCertificateRes checkCertificate() {
        Integer message;
        Integer details;
        Integer score;

        try {
           score = memberRepository.findByUsername(SecurityUtil.getLoginUsername()).get().getScore();
           log.info("Username: " + SecurityUtil.getLoginUsername() + ", Score: " + score);
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }

        if (score >= 70) {
            message = 0;
            details = 0;
        } else {
            message = 1;
            details = 70 - score;
        }

        return CheckCertificateRes.builder()
                .message(message)
                .details(details)
                .build();
    }

    public CertificateInfo makeCertificate() {
        Member member = memberRepository.findByUsername(SecurityUtil.getLoginUsername()).orElseThrow(
                () -> new IllegalArgumentException("로그인된 사용자가 DB에 없습니다.")
        );

        if (member.getScore() < 70) {
            throw new IllegalArgumentException("점수가 70점 미만인데 인증서 발급을 시도하려합니다.");
        }

        String rank = getRank(member.getScore());
        Optional<Certificate> oldCertificate = certificateRepository.findByMemberIdAndRank(member.getId(), rank);

        List<Post> posts = member.getPostList();

        if (posts.isEmpty()) {
            throw new IllegalArgumentException("이 사용자가 게시한 게시물 하나도 없습니다.");
        }

        Integer likes = 0;
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = LocalDateTime.MIN;

        for (Post post : posts) {
            likes += post.getLikes();
            if (post.getCreatedDate().isBefore(start)) {
                start = post.getCreatedDate();
            }
            if (post.getCreatedDate().isAfter(end)) {
                end = post.getCreatedDate();
            }
        }

        Certificate saved;

        if (oldCertificate.isPresent()) {
            saved = certificateRepository.save(oldCertificate.get().update(
                    member.getName(),
                    "Peer Mentoring",
                    start,
                    end.plusMonths(1),
                    posts.size(),
                    likes,
                    rank));
        } else {
            saved = certificateRepository.save(Certificate.builder()
                    .memberId(member.getId())
                    .name(member.getName())
                    .title("Peer Mentoring")
                    .start(start)
                    .end(end.plusMonths(1))
                    .lectures(posts.size())
                    .likes(likes)
                    .rank(rank)
                    .build());
        }

        return CertificateInfo.builder()
                .certificate_id(saved.getId())
                .name(saved.getName())
                .title(saved.getTitle())
                .start(saved.getStart().format(DateTimeFormatter.ofPattern("yyyy.MM.dd")))
                .end(saved.getEnd().format(DateTimeFormatter.ofPattern("yyyy.MM.dd")))
                .number_of_lectures(saved.getLectures())
                .number_of_referrals(saved.getLikes())
                .issue_date(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd")))
                .rank(saved.getRank())
                .build();
    }

    public String getRank(Integer score) {
        if (score >= 300) {
            return "Gold";
        } else if (score >= 150) {
            return "Silver";
        } else if (score >= 70) {
            return "Bronze";
        } else {
            throw new IllegalArgumentException("점수가 70점 미만인데 인증서 가능여부 로직을 통과했습니다.");
        }
    }
}
