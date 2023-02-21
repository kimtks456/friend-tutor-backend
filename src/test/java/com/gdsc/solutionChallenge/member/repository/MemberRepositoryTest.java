package com.gdsc.solutionChallenge.member.repository;

import static org.junit.jupiter.api.Assertions.*;

import com.gdsc.solutionChallenge.member.entity.Member;
import com.gdsc.solutionChallenge.member.entity.Role;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Transactional
class MemberRepositoryTest {

    @Autowired MemberRepository memberRepository;
    @Autowired EntityManager em;

    private void clear(){
        em.flush();
        em.clear();
    }

    @AfterEach
    public void afterEach() {
        em.clear();
    }

    @Test
    void 회원가입_성공() {
        // given
        Member member = Member.builder()
                .username("test")
                .password("test")
                .email("sdlkfsl@sdflsdkfj.dfm")
                .nickName("test")
                .grade(1)
                .role(Role.USER)
                .build();

        // when
        Member savedMember = memberRepository.save(member);

        // then
        Member findMember = memberRepository.findById(savedMember.getId()).orElseThrow(() -> new RuntimeException("저장된 회원이 없습니다"));//아직 예외 클래스를 만들지 않았기에 RuntimeException으로 처리하겠습니다.

        assertEquals(findMember, savedMember);
    }

    @Test
    void 회원가입_실패_username_없음() {
        // given
        Member member = Member.builder()
                .username("")
                .password("test")
                .email("sdlkfsl@sdflsdkfj.dfm")
                .nickName("test")
                .grade(1)
                .role(Role.USER)
                .build();

        // when, then
        assertThrows(Exception.class, () -> memberRepository.save(member));
    }

    @Test
    void 회원가입_실패_email_formmat() {
        // given
        Member member = Member.builder()
                .username("hello")
                .password("test")
                .email("sdlkfslsdflsㄴkfjdfm") // @ 있어야함.
                .nickName("test")
                .grade(1)
                .role(Role.USER)
                .build();

        // when, then
        assertThrows(Exception.class, () -> memberRepository.save(member));
    }

    @Test
    void 회원가입_실패_중복_username() {
        // given
        Member member1 = Member.builder()
                .username("hello")
                .password("test")
                .email("sdlkfslsdfl@ㄴkfjdfm") // @ 있어야함.
                .nickName("test")
                .grade(1)
                .role(Role.USER)
                .build();
        Member member2 = Member.builder()
                .username("hello")
                .password("test")
                .email("sdlkfslsdf@sㄴkfjdfm") // @ 있어야함.
                .nickName("test")
                .grade(1)
                .role(Role.USER)
                .build();

        memberRepository.save(member1);
        clear();

        // when, then
        assertThrows(Exception.class, () -> memberRepository.save(member2));
    }

    @Test
    void findByUsername_성공() {
        // given
        Member member = Member.builder()
                .username("hello")
                .password("test")
                .email("sdlkfslsdfl@ㄴkfjdfm") // @ 있어야함.
                .nickName("test")
                .grade(1)
                .role(Role.USER)
                .build();

        memberRepository.save(member);
        clear();

        // when
        Member findMember = memberRepository.findByUsername(member.getUsername()).orElseThrow(() -> new RuntimeException("저장된 회원이 없습니다"));

        // then
        assertEquals(findMember.toString(), member.toString());
    }

    @Test
    void 회원탈퇴_성공() {
        // given
        Member member = Member.builder()
                .username("hello")
                .password("test")
                .email("sdlkfslsdfl@ㄴkfjdfm") // @ 있어야함.
                .nickName("test")
                .grade(1)
                .role(Role.USER)
                .build();

        memberRepository.save(member);
        clear();

        // when
        memberRepository.delete(member);
        clear();

        // then
        assertThrows(Exception.class, () -> memberRepository.findById(member.getId()).orElseThrow(() -> new RuntimeException("저장된 회원이 없습니다")));
    }
}
