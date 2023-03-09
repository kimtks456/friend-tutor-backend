package com.gdsc.solutionChallenge.posts.service;

import com.gdsc.solutionChallenge.global.utils.SecurityUtil;
import com.gdsc.solutionChallenge.member.repository.MemberRepository;
import com.gdsc.solutionChallenge.posts.dto.res.CheckCertificateRes;
import com.gdsc.solutionChallenge.posts.repository.PostRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CertificateService {
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

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
}
