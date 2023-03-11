package com.gdsc.solutionChallenge.member.service;

import com.gdsc.solutionChallenge.global.config.JwtTokenProvider;
import com.gdsc.solutionChallenge.global.utils.SecurityUtil;
import com.gdsc.solutionChallenge.member.dto.MemberInfo;
import com.gdsc.solutionChallenge.member.dto.SignUpDto;
import com.gdsc.solutionChallenge.member.dto.TokenInfo;
import com.gdsc.solutionChallenge.member.entity.Member;
import com.gdsc.solutionChallenge.member.repository.MemberRepository;
import com.gdsc.solutionChallenge.posts.entity.Post;
import io.swagger.v3.oas.annotations.servers.Server;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional(readOnly = false)
@RequiredArgsConstructor
public class MemberService {

    @Autowired
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public TokenInfo login(String memberId, String password) {
        // 1. Login ID/PW 를 기반으로 Authentication 객체 생성
        // 이때 authentication 는 인증 여부를 확인하는 authenticated 값이 false
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(memberId, password);

        // 2. 실제 검증 (사용자 비밀번호 체크)이 이루어지는 부분
        // authenticate 매서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드가 실행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);

        return tokenInfo;
    }

    public String signup(SignUpDto signUpDto) {
        Member member = signUpDto.toEntity();
        member.addUserAuthority();

        member.encodePassword(passwordEncoder);

        if(memberRepository.findByUsername(signUpDto.username()).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
        }

        Member savedMember = memberRepository.save(member);
        return savedMember.getUsername();
    }

    public String withdraw(String checkPassword) throws Exception {
        Member member = memberRepository.findByUsername(SecurityUtil.getLoginUsername()).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 아이디입니다."));

        if (!member.matchPassword(passwordEncoder, checkPassword)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        memberRepository.delete(member);

        return member.getUsername();
    }

    public MemberInfo getUserInfo() {
        Member member = memberRepository.findByUsername(SecurityUtil.getLoginUsername()).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 아이디입니다."));

        return MemberInfo.builder()
                .member_id(member.getId())
                .username(member.getUsername())
                .name(member.getName())
                .nickName(member.getNickName())
                .grade(member.getGrade())
                .email(member.getEmail())
                .score(member.getScore())
                .build();
    }
}
