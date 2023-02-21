package com.gdsc.solutionChallenge.member.dto;

import com.gdsc.solutionChallenge.member.entity.Member;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SignUpDto (
        @NotBlank(message = "아이디를 입력해주세요.") String username,
        @NotBlank(message = "비밀번호를 입력해주세요.") String password,
        String name,
        @NotBlank(message = "닉네임을 입력해주세요.") String nickName,
        @NotNull(message = "학년을 입력해주세요.") Integer grade,
        @Email(message = "이메일을 입력해주세요.") String email) {

        public Member toEntity() {
                return Member.builder()
                        .username(username)
                        .password(password)
                        .name(name)
                        .nickName(nickName)
                        .grade(grade)
                        .email(email)
                        .build();
        }

}
