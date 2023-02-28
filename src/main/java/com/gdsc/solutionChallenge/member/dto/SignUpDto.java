package com.gdsc.solutionChallenge.member.dto;

import com.gdsc.solutionChallenge.member.entity.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(name = "회원가입 request body")
public record SignUpDto (
        @Schema(description = "아이디", example = "gdsc")
        @NotBlank(message = "아이디를 입력해주세요.") String username,
        @Schema(description = "비밀번호", example = "1234")
        @NotBlank(message = "비밀번호를 입력해주세요.") String password,
        @Schema(description = "실명", example = "홍길동 or null or blank")
        String name,
        @Schema(description = "닉네임", example = "산E")
        @NotBlank(message = "닉네임을 입력해주세요.") String nickName,
        @Schema(description = "학년", example = "6")
        @NotNull(message = "학년을 입력해주세요.") Integer grade,
        @Schema(description = "이메일", example = "kim@cc")
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
